package mk.gov.moepp.emi.invertoryinfo.mappers;

import mk.gov.moepp.emi.invertoryinfo.model.dto.GasDto;

import java.util.List;

public interface GasMapper {

    List<GasDto> getAllGasses();

    List<GasDto> getAllGassesFromYear(int yearId);

}
