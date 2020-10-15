package mk.gov.moepp.emi.invertoryinfo.model.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnalysisGasDto {

    //Kluc ni e imeto na gasot (primer CO2)
    HashMap<String, List<AnalysisCategoryGasDto>> gasHashMap = new HashMap<String, List<AnalysisCategoryGasDto>>();

    public AnalysisGasDto(){this.gasHashMap = new HashMap<>();}

    public AnalysisGasDto(HashMap<String, List<AnalysisCategoryGasDto>> gasHashMap) {
        this.gasHashMap = gasHashMap;
    }

    public HashMap<String, List<AnalysisCategoryGasDto>> getGasHashMap() {
        return gasHashMap;
    }

    public void setGasHashMap(HashMap<String, List<AnalysisCategoryGasDto>> gasHashMap) {
        this.gasHashMap = gasHashMap;
    }

    public void addNewAnalysis(String gasName, AnalysisCategoryGasDto analysisCategoryGas){

        gasHashMap.computeIfAbsent(gasName, key -> new ArrayList<>()).add(analysisCategoryGas);

    }

}
