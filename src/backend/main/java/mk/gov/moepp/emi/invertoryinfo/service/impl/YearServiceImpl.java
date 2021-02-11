package mk.gov.moepp.emi.invertoryinfo.service.impl;

import mk.gov.moepp.emi.invertoryinfo.model.exception.FileNotSupported;
import mk.gov.moepp.emi.invertoryinfo.model.exception.ResourceNotFound;
import mk.gov.moepp.emi.invertoryinfo.model.Year;
import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.model.requests.YearRequest;
import mk.gov.moepp.emi.invertoryinfo.model.requests.CreateAnalysisRequest;
import mk.gov.moepp.emi.invertoryinfo.repository.YearRepository;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisService;
import mk.gov.moepp.emi.invertoryinfo.service.YearService;
import mk.gov.moepp.emi.invertoryinfo.service.CategoryService;
import mk.gov.moepp.emi.invertoryinfo.service.GasService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class YearServiceImpl implements YearService {
    private final YearRepository yearRepository;
    private final CategoryService categoryService;
    private final GasService gasService;
    private final AnalysisService analysisService;

    public YearServiceImpl(YearRepository yearRepository, CategoryService categoryService, GasService gasService, AnalysisService analysisService) {
        this.yearRepository = yearRepository;
        this.categoryService = categoryService;
        this.gasService = gasService;
        this.analysisService = analysisService;
    }


    @Override
    public List<Year> getAllYears() {
        return yearRepository.findAll();
    }

    @Override
    public Year getYearById(int id) {
        return yearRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Year saveYear(Year year) {
        return yearRepository.save(year);
    }

    @Override
    public Year editYear(String year, String newYear) {
        Year model = yearRepository.findByYearEquals(year);
        if (model == null){
            throw new ResourceNotFound("Year not found");
        }
        model.setYear(newYear);
        return yearRepository.save(model);
    }

    @Override
    public void deleteYear(int id) {
       yearRepository.deleteById(id);
    }

    @Override
    public Year getByYear(String year) {
        return yearRepository.findByYearEquals(year);
    }

    @Override
    public List<Year> findAllByIds(List<Integer> list) {
        return yearRepository.findAllById(list);
    }


    @Override
    public Year saveFromFile(CreateAnalysisRequest request) {
        return null;
    }

    @Override
    @Transactional
    public void saveFromFileYearly(String year, MultipartFile file) {
        Year analysis = getAnalysis(year);

        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
            int numberOfSheets = workbook.getNumberOfSheets();
            if (numberOfSheets > 1) {
                throw new FileNotSupported("Only support files with 1 sheet");
            }
            XSSFSheet xssfSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = xssfSheet.rowIterator();
//            int howManyCategoriesInRow = Integer.MAX_VALUE;
            List<Gas> gasses = new ArrayList<>();
            List<Analysis> allAnalysis = new ArrayList<>();

            while (rowIterator.hasNext()){
             Row row = rowIterator.next();
             int rowNum = row.getRowNum();
             Gas gas;
             Category category = new Category();
             Analysis analysisCategoryGas;
             Iterator<Cell> cellIterator = row.cellIterator();
             while (cellIterator.hasNext()){
                 Cell cell = cellIterator.next();
                 int cellNum = cell.getColumnIndex();
                 if (cell.getCellType() == CellType._NONE || cell.getCellType() == CellType.BLANK ||cell.getCellType() == CellType.ERROR){
                     break;
                 }

                 //Reading gasses on row = 2
                 if (rowNum == 2 && cellNum > 0 && cell.getCellType() == CellType.STRING){
                     String gasName = cell.getStringCellValue();
                     if (!isEmptyString(gasName)) {
                         gasses.add(getGas(gasName));
                     }
                 }
                 //if row > 2 and type is string than that is category
                 else if (rowNum > 2 && cell.getCellType() == CellType.STRING) {
//                     howManyCategoriesInRow = cellNum + 1;
                     String categoryName = cell.getStringCellValue().trim();
                     if (!isEmptyString(categoryName))
                         category = getCategory(category, categoryName);
                 }
                 //if row > 2 and cell type is number than that is concentrate and we need to create analysis
                 else if (rowNum > 2 && isNumber(cell) && !gasses.isEmpty()){
                     double concentrate = cell.getNumericCellValue();
                     //go zimame prviot
                     gas = gasses.get(cellNum - 1);
                     if (gas != null && gas.getName() != null && category != null && category.getName() != null){
                         analysisCategoryGas = createAnalysisCategoryGas(analysis, category, gas, concentrate);
                         if (analysisCategoryGas != null){
                             allAnalysis.add(analysisCategoryGas);
                         }
                     }
                 }
             }
            }
            if (!allAnalysis.isEmpty()){
                analysisService.saveAllAnalysis(allAnalysis);
            }
        } catch (FileNotSupported fileNotSupported) {
            throw new FileNotSupported("File is not supported");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
            List<Year> years = new ArrayList<>();
            List<Analysis> allAnalysis = new ArrayList<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int rowNum = row.getRowNum();
                Category category = new Category();
                Analysis analysis;

                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int cellNum = cell.getColumnIndex();
                    //ako gi citame godinite
                    if (rowNum == 2 && isNumber(cell)){
                        double year = cell.getNumericCellValue();
                        String strYear = getYearFromString(String.valueOf(year));
                        years.add(getAnalysis(strYear));
                    }
                    else if (cell.getCellType() == CellType.STRING){
                        howManyCategoriesInRow = cellNum + 1;
                        String categoryName = cell.getStringCellValue().trim();
                        if (!isEmptyString(categoryName))
                            category = getCategory(category, categoryName);
                    }
                    else if (isNumber(cell) && howManyCategoriesInRow != Integer.MAX_VALUE){
                        double concentrate = cell.getNumericCellValue();
                        Year year = years.get(cellNum - howManyCategoriesInRow);
                        if (year != null && (category != null && category.getName() != null)) {
                            analysis = createAnalysisCategoryGas(year,category,gas,concentrate);
                            if (analysis != null){
                                allAnalysis.add(analysis);
                            }
                        }
                    } else if (cellNum > howManyCategoriesInRow){
                        break;
                    }
                }
            }

            if (!allAnalysis.isEmpty()){
                analysisService.saveAllAnalysis(allAnalysis);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Gas getGas(String name){
        Gas gas = gasService.findByNameEquals(name);
        if (gas != null){
            return gas;
        }
        gas = new Gas();
        gas.setName(name);
        return gasService.saveGas(gas);
    }

    private Category getCategory(Category category,String categoryName){
        Category oldCategory = categoryService.findByName(categoryName);

        if (oldCategory != null){
            category = oldCategory;
        }

        // dokolku ima - znaci ima nekoj prefix
        if (categoryName.contains("-")) {
            String prefix = categoryName.substring(0, categoryName.indexOf("-")).trim();
            category.setPrefix(prefix);
            //prebaruvame dali imame vekje nekoja kategorija so toj prefix da go smenime imeto
            oldCategory = categoryService.findByPrefix(category.getPrefix());
            if (oldCategory != null) {
                oldCategory.setName(categoryName);
                category = oldCategory;
            }
            //mestime subcategory
            if (prefix.contains(".")) {
                prefix = prefix.substring(0, prefix.lastIndexOf("."));
                Category parent = categoryService.findByPrefix(prefix);
                if (parent != null) {
                    category.setParent(parent);
                }
            }
        }
        category.setName(categoryName);

        return category;
    }

    private Analysis createAnalysisCategoryGas(Year year, Category category, Gas gas, double concentrate) {

        category = categoryService.saveCategory(category);

        Analysis analysis = analysisService.findByYearCategoryAndGas(year.getId(), category.getId(), gas.getId());

        //ako postoe vekje ovaa vrska togas samo da go dodadime concentrate
        if (analysis != null){
            if (analysis.getConcentrate() == concentrate) {
                return null;
            }
             analysis.setConcentrate(concentrate);
             return analysis;
        }

        analysis = new Analysis();
        analysis.setYear(year);
        analysis.setCategory(category);
        analysis.setGas(gas);
        analysis.setConcentrate(concentrate);

        return analysis;
    }

    private Year getAnalysis(String year) {
        Year analysis = yearRepository.findByYearEquals(year);
        if (analysis != null) {
            return analysis;
        }
        analysis = new Year();
        analysis.setYear(year);

        return yearRepository.save(analysis);
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

    private boolean isEmptyString(String text) {
        return text.isEmpty() || text.isBlank() || text.equals("");
    }
}
