package mk.gov.moepp.emi.invertoryinfo.service;

import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.requests.AnalysisRequest;
import mk.gov.moepp.emi.invertoryinfo.model.requests.CreateAnalysisRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnalysisService {

    List<Analysis> getAllAnalysis();

    Analysis getAnalysisById(int id);

    Analysis saveAnalysis(Analysis analysis);

    Analysis editAnalysis(int id, AnalysisRequest analysis);

    void deleteAnalysis(int id);

    Analysis getByYear(String year);

    List<Analysis> findAllByIds(List<Integer> list);

    Analysis saveFromFile(CreateAnalysisRequest request);

    void saveFromFile(MultipartFile file, String gasName);

}
