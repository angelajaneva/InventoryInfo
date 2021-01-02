package mk.gov.moepp.emi.invertoryinfo.repository;

import mk.gov.moepp.emi.invertoryinfo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface AnalysisCategoryGasRepository extends JpaRepository<AnalysisCategoryGas, Integer> {

    AnalysisCategoryGas findByAnalysis_IdAndCategory_IdAndGas_Id(int analysisId, int categoryId, int gasId);

    AnalysisCategoryGas findByAnalysis_IdAndCategory_IdAndGasName(int analysisId, int categoryId, String gasName);

    List<AnalysisCategoryGas> findByAnalysis_IdAndCategory_Id(int analysisId, int categoryId);

    List<AnalysisCategoryGas> findByGas_IdAndCategory_Id(int gasId, int categoryId);

    List<AnalysisCategoryGas> findAllByAnalysis_Id(int id);

    List<AnalysisCategoryGas> findAllByGas_Id(int id);

    List<AnalysisCategoryGas> findByGas_Name(String name);

    @Query("SELECT a FROM AnalysisCategoryGas a WHERE a.gas.id IN :gasIds AND a.category.id IN :categoryIds AND a.analysis.id IN :analysisIds")
    Set<AnalysisCategoryGas> findAllByIds(List<Integer> gasIds, List<Integer> categoryIds, List<Integer> analysisIds);

}
