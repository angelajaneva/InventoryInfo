package mk.gov.moepp.emi.invertoryinfo.mappers.impl;

import mk.gov.moepp.emi.invertoryinfo.model.exception.ResourceNotFound;
import mk.gov.moepp.emi.invertoryinfo.mappers.AnalysisMapper;
import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.AnalysisCategoryGas;
import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisCategoryGasDto;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisGasDto;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisYearlyDto;
import mk.gov.moepp.emi.invertoryinfo.model.dto.CategoryDto;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisCategoryGasService;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisService;
import mk.gov.moepp.emi.invertoryinfo.service.CategoryService;
import mk.gov.moepp.emi.invertoryinfo.service.GasService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisMapperImpl implements AnalysisMapper {

    private final AnalysisService analysisService;
    private final GasService gasService;
    private final CategoryService categoryService;
    private final AnalysisCategoryGasService analysisCategoryGasService;

    public AnalysisMapperImpl(AnalysisService analysisService, GasService gasService, CategoryService categoryService, AnalysisCategoryGasService analysisCategoryGasService) {
        this.analysisService = analysisService;
        this.gasService = gasService;
        this.categoryService = categoryService;
        this.analysisCategoryGasService = analysisCategoryGasService;
    }

    @Override
    public AnalysisYearlyDto getByYear(String year, List<Integer> gassesId, List<Integer> categoriesId) {
        Analysis analysis = analysisService.getByYear(year);
        if (analysis == null){
            throw new ResourceNotFound(year + " not found");
        }
        List<Gas> gasList = gasService.findAllByIds(gassesId);
        List<Category> categoryList = categoryService.findAllByIds(categoriesId);
        AnalysisYearlyDto analysisYearlyDto = new AnalysisYearlyDto();
        analysisYearlyDto.setYear(analysis.getYear());

        for (var gas : gasList){
            for (var category : categoryList){
                AnalysisCategoryGasDto analysisCategoryGasDto = mapToAnalysisCategoriesGasDto(analysis, category, gas);
                analysisYearlyDto.addNewAnalysis(gas.getName(), analysisCategoryGasDto);
            }
        }

        return analysisYearlyDto;
    }

    @Override
    public AnalysisGasDto getByGas(String gasName, List<Integer> analysisId, List<Integer> categoriesId) {
        Gas gas = gasService.findByNameEquals(gasName);
        if (gas == null){
            throw new ResourceNotFound(gasName + " not found");
        }
        List<Analysis> analysisList = analysisService.findAllByIds(analysisId);
        List<Category> categoryList = categoryService.findAllByIds(categoriesId);
        AnalysisGasDto analysisGasDto = new AnalysisGasDto();
        analysisGasDto.setGas(gas);

        for (Analysis analysis : analysisList) {
            for (Category category : categoryList) {
                AnalysisCategoryGasDto analysisCategoryGasDto = mapToAnalysisCategoriesGasDto(analysis, category, gas);
                analysisGasDto.addNewAnalysis(analysis, analysisCategoryGasDto);
            }
        }

        return analysisGasDto;
    }

    private AnalysisCategoryGasDto mapToAnalysisCategoriesGasDto(Analysis analysis, Category category, Gas gas){
        AnalysisCategoryGas analysisCategoryGas = analysisCategoryGasService.findByAnalysisCategoryAndGasName(analysis,category,gas);
        if (analysisCategoryGas == null){
            throw new ResourceNotFound("Relation not found");
        }
        CategoryDto categoryDto = new CategoryDto(category.getId(),category.getName(),category.getEnglishName(), category.getSubcategory() == null?-1:category.getSubcategory().getId());

        return new AnalysisCategoryGasDto(analysis.getYear(),gas.getName(),categoryDto,analysisCategoryGas.getConcentrate());
    }

}
