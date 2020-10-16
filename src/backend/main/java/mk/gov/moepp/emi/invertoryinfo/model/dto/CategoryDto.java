package mk.gov.moepp.emi.invertoryinfo.model.dto;

import mk.gov.moepp.emi.invertoryinfo.model.Category;

public class CategoryDto {
    private int id;
    private String mkName;
    private String enName;
    private String prefix;
    private Integer subcategoryId = null;

    public CategoryDto(int id, String mkName, String enName, String prefix, Integer subcategoryId) {
        this.id = id;
        this.mkName = mkName;
        this.enName = enName;
        this.prefix = prefix;
        this.subcategoryId = subcategoryId;
    }

    public String getMkName() {
        return mkName;
    }

    public void setMkName(String mkName) {
        this.mkName = mkName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getSubcategory() {
        return subcategoryId;
    }

    public void setSubcategory(int subcategory) {
        this.subcategoryId = subcategory;
    }
}
