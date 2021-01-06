package mk.gov.moepp.emi.invertoryinfo.service;

import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AnalysisService {

    List<Analysis> getAllAnalysis();

    Optional<Analysis> getAnalysis(int id);

    Analysis saveAnalysis(AnalysisDto dto);

    Analysis editAnalysis(int id, Analysis analysis);

    void deleteAnalysis(int id);

    List<Analysis> findByYearAndCategory(int year, int category);

    List<Analysis> findByGasAndCategory(int gas, int category);

    Analysis findByYearCategoryAndGas(int year, int category, int gas);

    Analysis findByYearCategoryAndGasName(int year, int category, String gas);

    List<Analysis> findAllByGas (int id);

    List<Analysis> saveAllAnalysis(List<Analysis> analyses);

    Set<Analysis> findAllByIds(List<Integer> gasIds, List<Integer> categoryIds, List<Integer> yearsId);
}
