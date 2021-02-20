package mk.gov.moepp.emi.invertoryinfo.service.impl;

import mk.gov.moepp.emi.invertoryinfo.model.Year;
import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisDto;
import mk.gov.moepp.emi.invertoryinfo.repository.AnalysisRepository;
import mk.gov.moepp.emi.invertoryinfo.repository.YearRepository;
import mk.gov.moepp.emi.invertoryinfo.repository.CategoryRepository;
import mk.gov.moepp.emi.invertoryinfo.repository.GasRepository;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final CategoryRepository categoryRepository;
    private final GasRepository gasRepository;
    private final YearRepository yearRepository;

    public AnalysisServiceImpl(AnalysisRepository analysisRepository, CategoryRepository categoryRepository, GasRepository gasRepository, YearRepository yearRepository) {
        this.analysisRepository = analysisRepository;
        this.categoryRepository = categoryRepository;
        this.gasRepository = gasRepository;
        this.yearRepository = yearRepository;
    }

    @Override
    public List<Analysis> getAllAnalysis() {
        return analysisRepository.findAll();
    }

    @Override
    public Optional<Analysis> getAnalysis(int id) {
        return analysisRepository.findById(id);
    }

    @Override
    @Transactional
    public Analysis saveAnalysis(AnalysisDto dto) {
        Year year = yearRepository.findByYearEquals(dto.getYear());
        Category category = categoryRepository.findByNameEquals(dto.getCategory().getName());
        Gas gas = gasRepository.findByNameEquals(dto.getGasName());

        System.out.println(year);
        System.out.println(category);
        System.out.println(gas);

        if (year != null && category != null && gas != null) {
            year = yearRepository.save(year);
            category = categoryRepository.save(category);
            gas = gasRepository.save(gas);

            Analysis analysis = new Analysis();
            analysis.setYear(year);
            analysis.setCategory(category);
            analysis.setGas(gas);
            analysis.setConcentrate(dto.getConcentrate());
            return analysisRepository.save(analysis);
        }
        else throw new ResourceNotFoundException("Analysis, Category or Gas cant be null");
    }

    @Override
    @Transactional
    public Analysis editAnalysis(int id, Analysis analysis) {
        Analysis analysis1 = analysisRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        analysis1.setCategory(analysis.getCategory());
        analysis1.setGas(analysis.getGas());
        analysis1.setYear(analysis.getYear());
        analysis1.setConcentrate(analysis.getConcentrate());

        return analysisRepository.save(analysis1);
    }

    @Override
    @Transactional
    public void deleteAnalysis(int id) {
        analysisRepository.deleteById(id);
    }

    @Override
    public List<Analysis> findByYearAndCategory(int year, int category) {
        return analysisRepository.findAllByYear_IdAndCategory_Id(year, category);
    }

    @Override
    public List<Analysis> findByGasAndCategory(int gas, int category) {
        return analysisRepository.findByGas_IdAndCategory_Id(gas, category);
    }

    @Override
    public Analysis findByYearCategoryAndGas(int year, int category, int gas) {
        return analysisRepository.findAllByYear_IdAndCategory_IdAndGas_Id(year,category,gas);
    }

    @Override
    public Analysis findByYearCategoryAndGasName(int year, int category, String gas) {
        return analysisRepository.findByYear_IdAndCategory_IdAndGasName(year,category,gas);
    }

    @Override
    public List<Analysis> findAllByGas(int id) {
        return analysisRepository.findAllByGas_Id(id);
    }

    @Override
    @Transactional
    public List<Analysis> saveAllAnalysis(List<Analysis> analyses) {
        return analysisRepository.saveAll(analyses);
    }

    @Override
    public Set<Analysis> findAllByIds(List<Integer> gasIds, List<Integer> categoryIds, List<Integer> yearsId) {
        return analysisRepository.findAllByIds(gasIds,categoryIds,yearsId);
    }

}
