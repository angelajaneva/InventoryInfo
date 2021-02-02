package mk.gov.moepp.emi.invertoryinfo.service;

import mk.gov.moepp.emi.invertoryinfo.model.Year;
import mk.gov.moepp.emi.invertoryinfo.model.requests.YearRequest;
import mk.gov.moepp.emi.invertoryinfo.model.requests.CreateAnalysisRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface YearService {

    List<Year> getAllYears();

    Year getYearById(int id);

    Year saveYear(Year year);

    Year editYear(int id, YearRequest analysis);

    void deleteYear(int id);

    Year getByYear(String year);

    List<Year> findAllByIds(List<Integer> list);

    Year saveFromFile(CreateAnalysisRequest request);

    void saveFromFileYearly(String year, MultipartFile file);

    void saveFromFile(MultipartFile file, String gasName);

}
