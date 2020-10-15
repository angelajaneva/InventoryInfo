package mk.gov.moepp.emi.invertoryinfo.service;

import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.model.dto.GasDto;
import mk.gov.moepp.emi.invertoryinfo.model.requests.GasRequest;
import mk.gov.moepp.emi.invertoryinfo.repository.GasRepository;

import java.util.List;

public interface GasService {

    List<Gas> getAllGasses();

    Gas getGas(int id);

    Gas saveGas(Gas gas);

    Gas editGas(int id, GasRequest gas);

    void deleteGas(int id);

    Gas findByNameEquals(String name);

    List<Gas> findAllByIds(List<Integer> list);

}
