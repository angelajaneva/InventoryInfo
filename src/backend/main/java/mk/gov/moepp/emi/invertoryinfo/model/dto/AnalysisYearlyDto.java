package mk.gov.moepp.emi.invertoryinfo.model.dto;

import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisCategoryGasDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnalysisYearlyDto {

    String year;
    // Kluc ni e imeto na gasot a listata ni e categoria i concentrate
    HashMap<String, List<AnalysisCategoryGasDto>> analysisHashMap = new HashMap<>();

    public AnalysisYearlyDto(){analysisHashMap = new HashMap<>();}

    public AnalysisYearlyDto(HashMap<String, List<AnalysisCategoryGasDto>> hashMap) {
        this.analysisHashMap = hashMap;
    }

    public HashMap<String, List<AnalysisCategoryGasDto>> getHashMap() {
        return analysisHashMap;
    }

    public void setHashMap(HashMap<String, List<AnalysisCategoryGasDto>> hashMap) {
        this.analysisHashMap = hashMap;
    }

    public void addNewAnalysis(String gas, AnalysisCategoryGasDto analysisCategoryGas){

        analysisHashMap.computeIfAbsent(year, key -> new ArrayList<>()).add(analysisCategoryGas);

    }

}
