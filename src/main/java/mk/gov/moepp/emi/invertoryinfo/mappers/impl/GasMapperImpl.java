package mk.gov.moepp.emi.invertoryinfo.mappers.impl;

import mk.gov.moepp.emi.invertoryinfo.mappers.GasMapper;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.model.dto.GasDto;
import mk.gov.moepp.emi.invertoryinfo.repository.AnalysisRepository;
import mk.gov.moepp.emi.invertoryinfo.repository.GasRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GasMapperImpl implements GasMapper {

    private final GasRepository gasRepository;
    private final AnalysisRepository analysisRepository;

    public GasMapperImpl(GasRepository gasRepository, AnalysisRepository analysisRepository) {
        this.gasRepository = gasRepository;
        this.analysisRepository = analysisRepository;
    }

    @Override
    public List<GasDto> getAllGasses() {
        List<Gas> gasses = gasRepository.findAll();
        return mapToGasDto(gasses);
    }

    @Override
    public List<GasDto> getAllGassesFromYear(int yearId) {
        var analysisCategoryGas = analysisRepository.findAllByYear_Id(yearId);
        List<Integer> list = new ArrayList<>();
        for (var analysis : analysisCategoryGas) {
            list.add(analysis.getGas().getId());
        }
        var gasses = gasRepository.findAllById(list);
        return mapToGasDto(gasses);
    }

    private List<GasDto> mapToGasDto(List<Gas> gases) {
        return gases.stream()
                .map(g -> new GasDto(g.getId(),g.getName(),false))
                .collect(Collectors.toList());
    }
}
