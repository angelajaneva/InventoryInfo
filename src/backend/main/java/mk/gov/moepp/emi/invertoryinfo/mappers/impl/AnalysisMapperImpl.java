package mk.gov.moepp.emi.invertoryinfo.mappers.impl;

import mk.gov.moepp.emi.invertoryinfo.mappers.AnalysisMapper;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisDto;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisGasDto;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisYearlyDto;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisService;
import mk.gov.moepp.emi.invertoryinfo.service.CategoryService;
import mk.gov.moepp.emi.invertoryinfo.service.GasService;
import org.springframework.beans.factory.annotation.Autowired;

public class AnalysisMapperImpl implements AnalysisMapper {

    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private GasService gasService;
    @Autowired
    private CategoryService categoryService;


    @Override
    public AnalysisDto getAnalysisDto() {
        AnalysisDto analysisDto = new AnalysisDto();

        AnalysisGasDto analysisGasDto = new AnalysisGasDto();
        AnalysisYearlyDto analysisYearlyDto = new AnalysisYearlyDto();


        return null;
    }
}
