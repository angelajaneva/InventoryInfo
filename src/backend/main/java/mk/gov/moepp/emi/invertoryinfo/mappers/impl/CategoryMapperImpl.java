package mk.gov.moepp.emi.invertoryinfo.mappers.impl;

import mk.gov.moepp.emi.invertoryinfo.mappers.CategoryMapper;
import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.dto.CategoryDto;
import mk.gov.moepp.emi.invertoryinfo.model.dto.NavigationCategoriesDto;
import mk.gov.moepp.emi.invertoryinfo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryMapperImpl implements CategoryMapper {

    private final CategoryService categoryService;

    public CategoryMapperImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categoryList = categoryService.findAll();
        List<CategoryDto> navigationCategoriesDtos = new ArrayList<>();

        for(Category category : categoryList){
            navigationCategoriesDtos.add(mapToCategoriyDto(category));
        }

        return navigationCategoriesDtos;
    }

    CategoryDto mapToCategoriyDto(Category category){
        //ako podkategorijata e null napravi go -1 znaci nema podkategorija
        return new CategoryDto(category.getId(),category.getName(),category.getEnglishName(),category.getSubcategory() == null ? -1 : category.getSubcategory().getId());
    }

    NavigationCategoriesDto mapToNavigationCategoriesDto(Category category){
        List<Category> subcategories = categoryService.findAllBySubcategory(category.getId());
        List<NavigationCategoriesDto> navigationCategoriesDtos = new ArrayList<>();
        for (Category cat : subcategories){
            navigationCategoriesDtos.add(mapToNavigationCategoriesDto(cat));
        }
        return new NavigationCategoriesDto(category.getId(), category.getName(),category.getEnglishName(),category.getPrefix(),navigationCategoriesDtos);
    }
}
