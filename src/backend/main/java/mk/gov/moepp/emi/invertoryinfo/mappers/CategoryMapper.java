package mk.gov.moepp.emi.invertoryinfo.mappers;

import mk.gov.moepp.emi.invertoryinfo.model.dto.CategoryDto;

import java.util.List;

public interface CategoryMapper {

    List<CategoryDto> getAllCategories();

}
