package mk.gov.moepp.emi.invertoryinfo.service;

import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.model.dto.GasDto;

import java.util.List;

public interface GasService {

    List<Gas> getAllGasses();

    Gas getGas(int id);

    Gas saveGas(Gas gas);

    Gas editGas(int id, GasDto gas);

    void deleteGas(int id);

    Gas findByNameEquals(String name);

}
