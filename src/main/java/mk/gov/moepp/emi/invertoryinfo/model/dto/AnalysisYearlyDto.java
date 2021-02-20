package mk.gov.moepp.emi.invertoryinfo.model.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnalysisYearlyDto {

    String year;
    // Kluc ni e imeto na gasot a listata ni e categoria i concentrate
    HashMap<String, List<AnalysisDto>> analysisHashMap = new HashMap<>();

    public AnalysisYearlyDto(){analysisHashMap = new HashMap<>();}

    public AnalysisYearlyDto(HashMap<String, List<AnalysisDto>> hashMap) {
        this.analysisHashMap = hashMap;
    }

    public HashMap<String, List<AnalysisDto>> getHashMap() {
        return analysisHashMap;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setHashMap(HashMap<String, List<AnalysisDto>> hashMap) {
        this.analysisHashMap = hashMap;
    }

    public void addNewAnalysis(String gas, AnalysisDto analysisCategoryGas){

        analysisHashMap.computeIfAbsent(gas, key -> new ArrayList<>()).add(analysisCategoryGas);

    }

}
