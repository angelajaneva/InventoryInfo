package mk.gov.moepp.emi.invertoryinfo.mappers;

import mk.gov.moepp.emi.invertoryinfo.model.dto.YearDto;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisGasDto;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisYearlyDto;

import java.util.List;

public interface AnalysisMapper {

    AnalysisYearlyDto getByYear(String year, List<Integer> gassesId, List<Integer> categoriesId);

    AnalysisGasDto getByGas(String gasName, List<Integer> gassesId, List<Integer> categoriesId);

    List<YearDto> getByGasId(int gasId);

    List<YearDto> getAllYears();
}
