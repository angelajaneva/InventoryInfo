package mk.gov.moepp.emi.invertoryinfo.service;

import mk.gov.moepp.emi.invertoryinfo.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategory(int id);

    Category saveCategory(Category category);

    Category editCategory(Category category);

    void deleteCategory(int id);

    Category getCategoryByName(String name);

    Category getCategoryByEnglishName(String name);

    Category findByPrefix(String prefix);

    Category findByName(String name);

    Category findByEnglishName(String englishName);

    List<Category> findAllByIds(List<Integer> list);

}
