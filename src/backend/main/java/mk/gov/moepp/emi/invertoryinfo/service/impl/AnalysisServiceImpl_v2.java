package mk.gov.moepp.emi.invertoryinfo.service.impl;

import mk.gov.moepp.emi.invertoryinfo.exception.FileNotSupported;
import mk.gov.moepp.emi.invertoryinfo.exception.ResourceNotFound;
import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.AnalysisCategoryGas;
import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.model.requests.CreateAnalysisRequest;
import mk.gov.moepp.emi.invertoryinfo.model.enums.FileType;
import mk.gov.moepp.emi.invertoryinfo.repository.AnalysisRepository;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisCategoryGasService;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisService;
import mk.gov.moepp.emi.invertoryinfo.service.CategoryService;
import mk.gov.moepp.emi.invertoryinfo.service.GasService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AnalysisServiceImpl_v2 implements AnalysisService {
    private final AnalysisRepository analysisRepository;
    private final CategoryService categoryService;
    private final GasService gasService;
    private final AnalysisCategoryGasService analysisCategoryGasService;
    private final int MK_NAME = 0;

    public AnalysisServiceImpl_v2(AnalysisRepository analysisRepository, CategoryService categoryService, GasService gasService, AnalysisCategoryGasService analysisCategoryGasService) {
        this.analysisRepository = analysisRepository;
        this.categoryService = categoryService;
        this.gasService = gasService;
        this.analysisCategoryGasService = analysisCategoryGasService;
    }


    @Override
    public List<Analysis> getAllAnalysis() {
        return analysisRepository.findAll();
    }

    @Override
    public Analysis getAnalysisById(int id) {
        return analysisRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Analysis saveAnalysis(Analysis analysis) {
        return analysisRepository.save(analysis);
    }

    @Override
    public Analysis editAnalysis(Analysis analysis) {
        return analysisRepository.save(analysis);
    }

    @Override
    public void deleteAnalysis(int id) {
        Analysis analysis = analysisRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        analysis.setDeleted(true);
        analysisRepository.save(analysis);
    }


    @Override
    public Analysis saveFromFile(CreateAnalysisRequest request) {
        return null;
    }

    @Override
    @Transactional
    public void saveFromFile(MultipartFile file, String gasName) {
        Gas gas = gasService.findByNameEquals(gasName);
        if (gas == null){
            throw new ResourceNotFound(gasName + " not found");
        }
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            int numberOfSheets = workbook.getNumberOfSheets();
            if (numberOfSheets > 1) {
                throw new FileNotSupported("Only support files with 1 sheet");
            }
            XSSFSheet xssfSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = xssfSheet.rowIterator();

            //doznavame na koj red ni se kategoriite
            int howManyCategoriesInRow = Integer.MAX_VALUE;
            List<Analysis> years = new ArrayList<>();
            List<AnalysisCategoryGas> allAnalysis = new ArrayList<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int rowNum = row.getRowNum();
                Category category = new Category();
                AnalysisCategoryGas analysisCategoryGas;

                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int cellNum = cell.getColumnIndex();
                    //ako gi citame godinite
                    if (rowNum == 2 && isNumber(cell)){
                        double year = cell.getNumericCellValue();
                        String strYear = getYearFromString(String.valueOf(year));
                        years.add(getAnalysis(strYear));
                    } else if (cell.getCellType() == CellType.STRING){
                        howManyCategoriesInRow = cellNum + 1;
                        String categoryName = cell.getStringCellValue();
                        if (!emptyString(categoryName))
                            category = getCategory(categoryName,cellNum);
                    } else if (isNumber(cell) && howManyCategoriesInRow != Integer.MAX_VALUE){
                        double concentrate = cell.getNumericCellValue();
                        Analysis analysis = years.get(cellNum - howManyCategoriesInRow);
                        if (analysis != null && (category != null && (category.getName() != null || category.getEnglishName() != null))) {
                            analysisCategoryGas = createAnalysisCategoryGas(analysis,category,gas,concentrate);
                            if (analysisCategoryGas != null){
                                allAnalysis.add(analysisCategoryGas);
                            }
                        }
                    } else if (cellNum > howManyCategoriesInRow){
                        break;
                    }
                }
            }

            if (!allAnalysis.isEmpty()){
                analysisCategoryGasService.saveAllAnalysisCategoryGas(allAnalysis);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Category getCategory(String categoryName, int cellNum){
        Category category;
        if (cellNum == MK_NAME){
            category = categoryService.findByName(categoryName);
        } else {
            category = categoryService.findByEnglishName(categoryName);
        }
        if (category == null){
            category = new Category();
            if (cellNum == MK_NAME){
                category.setName(categoryName);
            } else {
                category.setEnglishName(categoryName);
            }
        }

        // dokolku ima - znaci ima nekoj prefix
        if (categoryName.contains("-")) {
            String prefix = categoryName.substring(0, categoryName.indexOf("-")).trim();
            category.setPrefix(prefix);
            //prebaruvame dali imame vekje nekoja kategorija so toj prefix i dokolku go nema angliskoto ili makedonskoto ime da se stavi
            Category oldCategory = categoryService.findByPrefix(category.getPrefix());
            if (oldCategory != null) {
                if (cellNum == MK_NAME){
                    oldCategory.setName(categoryName);
                } else {
                    oldCategory.setEnglishName(categoryName);
                }
                category = oldCategory;
            }
            //mestime subcategory
            if (prefix.contains(".")) {
                prefix = prefix.substring(0, prefix.lastIndexOf("."));
                Category subcategory = categoryService.findByPrefix(prefix);
                if (subcategory != null) {
                    category.setSubcategory(subcategory);
                }
            }
        }

        return category;
    }


    private AnalysisCategoryGas createAnalysisCategoryGas(Analysis analysis, Category category, Gas gas, double concentrate) {

        category = categoryService.saveCategory(category);

        AnalysisCategoryGas analysisCategoryGas = analysisCategoryGasService.findByAnalysisCategoryAndGas(analysis, category, gas);

        //ako postoe vekje ovaa vrska togas samo da go dodadime concentrate
        if (analysisCategoryGas != null){
            if (analysisCategoryGas.getConcentrate() == concentrate) {
                return null;
            }
             analysisCategoryGas.setConcentrate(concentrate);
             return analysisCategoryGas;
        }

        analysisCategoryGas = new AnalysisCategoryGas();
        analysisCategoryGas.setAnalysis(analysis);
        analysisCategoryGas.setCategory(category);
        analysisCategoryGas.setGas(gas);
        analysisCategoryGas.setConcentrate(concentrate);

        return analysisCategoryGas;
    }

    private Analysis getAnalysis(String year) {
        Analysis analysis = analysisRepository.findByYearEquals(year);
        if (analysis != null) {
            return analysis;
        }
        analysis = new Analysis();
        analysis.setYear(year);

        return analysisRepository.save(analysis);
    }

    private String getYearFromString(String strYear) {
        //dokolku a zeme double vrednost ima 1990.00 a da parsiram vo integer mi dava error
        if (strYear.contains(".")) {
            strYear = strYear.substring(0, strYear.indexOf("."));
        }
        return strYear;
    }

    private boolean isNumber(Cell cell) {
        return cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA;
    }

    private boolean emptyString(String text) {
        return text.isEmpty() || text.isBlank() || text.equals("");
    }
}
