package mk.gov.moepp.emi.invertoryinfo.model.dto;

import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnalysisGasDto {

    Gas gas;
    //Kluc ni e godinata (primer 2000) a listata ni e categoriite so concentrate
    HashMap<Analysis, List<AnalysisCategoryGasDto>> gasHashMap = new HashMap<Analysis, List<AnalysisCategoryGasDto>>();

    public AnalysisGasDto(){this.gasHashMap = new HashMap<>();}

    public AnalysisGasDto(HashMap<Analysis, List<AnalysisCategoryGasDto>> gasHashMap, Gas gas) {
        this.gasHashMap = gasHashMap;
        this.gas = gas;
    }

    public Gas getGas() {
        return gas;
    }

    public void setGas(Gas gas) {
        this.gas = gas;
    }

    public HashMap<Analysis, List<AnalysisCategoryGasDto>> getGasHashMap() {
        return gasHashMap;
    }

    public void setGasHashMap(HashMap<Analysis, List<AnalysisCategoryGasDto>> gasHashMap) {
        this.gasHashMap = gasHashMap;
    }

    public void addNewAnalysis(Analysis year, AnalysisCategoryGasDto analysisCategoryGasDto){
        gasHashMap.computeIfAbsent(year, key -> new ArrayList<>()).add(analysisCategoryGasDto);
    }

}
