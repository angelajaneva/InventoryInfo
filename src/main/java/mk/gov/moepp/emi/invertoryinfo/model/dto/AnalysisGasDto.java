package mk.gov.moepp.emi.invertoryinfo.model.dto;

import mk.gov.moepp.emi.invertoryinfo.model.Year;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnalysisGasDto {

    Gas gas;
    //Kluc ni e godinata (primer 2000) a listata ni e categoriite so concentrate
    HashMap<Year, List<AnalysisDto>> gasHashMap = new HashMap<Year, List<AnalysisDto>>();

    public AnalysisGasDto(){this.gasHashMap = new HashMap<>();}

    public AnalysisGasDto(HashMap<Year, List<AnalysisDto>> gasHashMap, Gas gas) {
        this.gasHashMap = gasHashMap;
        this.gas = gas;
    }

    public Gas getGas() {
        return gas;
    }

    public void setGas(Gas gas) {
        this.gas = gas;
    }

    public HashMap<Year, List<AnalysisDto>> getGasHashMap() {
        return gasHashMap;
    }

    public void setGasHashMap(HashMap<Year, List<AnalysisDto>> gasHashMap) {
        this.gasHashMap = gasHashMap;
    }

    public void addNewAnalysis(Year year, AnalysisDto analysisDto){
        gasHashMap.computeIfAbsent(year, key -> new ArrayList<>()).add(analysisDto);
    }

}
