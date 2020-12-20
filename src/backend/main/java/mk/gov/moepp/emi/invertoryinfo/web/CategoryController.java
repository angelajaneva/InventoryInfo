package mk.gov.moepp.emi.invertoryinfo.web;

import mk.gov.moepp.emi.invertoryinfo.mappers.CategoryMapper;
import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.dto.CategoryDto;
import mk.gov.moepp.emi.invertoryinfo.model.dto.NavigationCategoriesDto;
import mk.gov.moepp.emi.invertoryinfo.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/category", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class CategoryController {

    //I tuka koki mi rece nemame potreba od via site post edit delete
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public List<CategoryDto> getAllCategories(){
        return  categoryMapper.getAllCategories();
    }

    @GetMapping(path = "/{id}")
    public Category getCategory(@PathVariable int id){
        return categoryService.getCategory(id);
    }

    @PostMapping
    public Category saveCategory(@RequestBody Category category){
        return categoryService.saveCategory(category);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable int id){
        categoryService.deleteCategory(id);
    }
}
