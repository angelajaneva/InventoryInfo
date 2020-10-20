package mk.gov.moepp.emi.invertoryinfo.service;

import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.AnalysisCategoryGas;
import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisCategoryGasDto;

import java.util.List;

public interface AnalysisCategoryGasService {

    List<AnalysisCategoryGas> getAllAnalysisCategoryGas();

    AnalysisCategoryGas getAnalysisCategoryGasById(int id);

    AnalysisCategoryGas saveAnalysisCategoryGas(AnalysisCategoryGasDto dto);

    AnalysisCategoryGas editAnalysisCategoryGas(AnalysisCategoryGas analysisCategoryGas);

    void deleteAnalysisCategoryGas(int id);

    List<AnalysisCategoryGas> findByAnalysisAndCategory(Analysis analysis, Category category);

    List<AnalysisCategoryGas> findByGasAndCategory(Gas gas, Category category);

    AnalysisCategoryGas findByAnalysisCategoryAndGas(Analysis analysis, Category category, Gas gas);

    AnalysisCategoryGas findByAnalysisCategoryAndGasName(Analysis analysis, Category category, Gas gas);

    List<AnalysisCategoryGas> saveAllAnalysisCategoryGas(List<AnalysisCategoryGas> analysisCategoryGases);
}
