package mk.gov.moepp.emi.invertoryinfo.repository;

import mk.gov.moepp.emi.invertoryinfo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface AnalysisRepository extends JpaRepository<Analysis, Integer> {

    Analysis findAllByYear_IdAndCategory_IdAndGas_Id(int yearId, int categoryId, int gasId);

    Analysis findByYear_IdAndCategory_IdAndGasName(int analysisId, int categoryId, String gasName);

    List<Analysis> findAllByYear_IdAndCategory_Id(int yearId, int categoryId);

    List<Analysis> findByGas_IdAndCategory_Id(int gasId, int categoryId);

    List<Analysis> findAllByYear_Id(int id);

    List<Analysis> findAllByGas_Id(int id);

    @Query("SELECT a FROM Analysis a WHERE a.gas.id IN :gasIds AND a.category.id IN :categoryIds AND a.year.id IN :analysisIds")
    Set<Analysis> findAllByIds(List<Integer> gasIds, List<Integer> categoryIds, List<Integer> analysisIds);

}
