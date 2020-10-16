package mk.gov.moepp.emi.invertoryinfo.model.dto;

import java.util.ArrayList;
import java.util.List;

public class AnalysisDto {

    List<AnalysisGasDto> gasDtoList = new ArrayList<>();
    List<AnalysisYearlyDto> yearlyDtoList = new ArrayList<>();

    public List<AnalysisGasDto> getGasDtoList() {
        return gasDtoList;
    }

    public void setGasDtoList(List<AnalysisGasDto> gasDtoList) {
        this.gasDtoList = gasDtoList;
    }

    public List<AnalysisYearlyDto> getYearlyDtoList() {
        return yearlyDtoList;
    }

    public void setYearlyDtoList(List<AnalysisYearlyDto> yearlyDtoList) {
        this.yearlyDtoList = yearlyDtoList;
    }
}
