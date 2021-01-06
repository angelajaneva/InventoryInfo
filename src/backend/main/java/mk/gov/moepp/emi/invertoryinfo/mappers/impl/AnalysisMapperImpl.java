package mk.gov.moepp.emi.invertoryinfo.mappers.impl;

import mk.gov.moepp.emi.invertoryinfo.model.dto.*;
import mk.gov.moepp.emi.invertoryinfo.model.exception.ResourceNotFound;
import mk.gov.moepp.emi.invertoryinfo.mappers.AnalysisMapper;
import mk.gov.moepp.emi.invertoryinfo.model.Year;
import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisService;
import mk.gov.moepp.emi.invertoryinfo.service.YearService;
import mk.gov.moepp.emi.invertoryinfo.service.CategoryService;
import mk.gov.moepp.emi.invertoryinfo.service.GasService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalysisMapperImpl implements AnalysisMapper {

    private final YearService yearService;
    private final GasService gasService;
    private final CategoryService categoryService;
    private final AnalysisService analysisService;

    public AnalysisMapperImpl(YearService yearService, GasService gasService, CategoryService categoryService, AnalysisService analysisService) {
        this.yearService = yearService;
        this.gasService = gasService;
        this.categoryService = categoryService;
        this.analysisService = analysisService;
    }

    @Override
    public AnalysisYearlyDto getByYear(String year, List<Integer> gassesId, List<Integer> categoriesId) {
        Year analysis = yearService.getByYear(year);
        if (analysis == null){
            throw new ResourceNotFound(year + " not found");
        }
        List<Gas> gasList = gasService.findAllByIds(gassesId);
        List<Category> categoryList = categoryService.findAllByIds(categoriesId);
        AnalysisYearlyDto analysisYearlyDto = new AnalysisYearlyDto();
        analysisYearlyDto.setYear(analysis.getYear());

        for (var gas : gasList){
            for (var category : categoryList){
                AnalysisDto analysisDto = mapToAnalysisCategoriesGasDto(analysis, category, gas);
                analysisYearlyDto.addNewAnalysis(gas.getName(), analysisDto);
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
        List<Year> yearList = yearService.findAllByIds(analysisId);
        List<Category> categoryList = categoryService.findAllByIds(categoriesId);
        AnalysisGasDto analysisGasDto = new AnalysisGasDto();
        analysisGasDto.setGas(gas);

        for (Year year : yearList) {
            for (Category category : categoryList) {
                AnalysisDto analysisDto = mapToAnalysisCategoriesGasDto(year, category, gas);
                analysisGasDto.addNewAnalysis(year, analysisDto);
            }
        }

        return analysisGasDto;
    }

    @Override
    public List<YearDto> getByGasId(int gasId) {
        var analysisCategoryGas = analysisService.findAllByGas(gasId);
        List<Integer> list = new ArrayList<>();
        for (var analysis :analysisCategoryGas) {
            list.add(analysis.getYear().getId());
        }
        var analysis = yearService.findAllByIds(list);
        return analysis.stream()
                .map(a -> new YearDto(a.getId(),a.getYear(), false))
                .collect(Collectors.toList());
    }

    @Override
    public List<YearDto> getAllYears() {
        var analysisCategoryGas = analysisService.getAllAnalysis();
        List<Integer> list = new ArrayList<>();
        for (var analysis :analysisCategoryGas) {
            list.add(analysis.getYear().getId());
        }
        var analysis = yearService.findAllByIds(list);
        return analysis.stream()
                .map(a -> new YearDto(a.getId(),a.getYear(), false))
                .collect(Collectors.toList());
    }

    private AnalysisDto mapToAnalysisCategoriesGasDto(Year year, Category category, Gas gas){
        Analysis analysis = analysisService.findByYearCategoryAndGasName(year.getId(),category.getId(),gas.getName());
//        if (analysisCategoryGas == null){
//            throw new ResourceNotFound("Relation not found");
//        }
        CategoryDto categoryDto = new CategoryDto(
                category.getId(),
                category.getName(),
                category.getParent() == null? -1 : category.getParent().getId());

        return new AnalysisDto(year.getYear(),gas.getName(),categoryDto, analysis == null ? 0 : analysis.getConcentrate());
    }

}
