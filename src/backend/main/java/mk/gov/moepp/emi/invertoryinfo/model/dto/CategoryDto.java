package mk.gov.moepp.emi.invertoryinfo.model.dto;

public class CategoryDto {
    private String mk_name;
    private String en_name;
    private String prefix;
    private CategoryDto subcategory;

    public CategoryDto(String mk_name, String en_name, String prefix, CategoryDto subcategory) {
        this.mk_name = mk_name;
        this.en_name = en_name;
        this.prefix = prefix;
        this.subcategory = subcategory;
    }

    public String getMk_name() {
        return mk_name;
    }

    public void setMk_name(String mk_name) {
        this.mk_name = mk_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public CategoryDto getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(CategoryDto subcategory) {
        this.subcategory = subcategory;
    }
}
