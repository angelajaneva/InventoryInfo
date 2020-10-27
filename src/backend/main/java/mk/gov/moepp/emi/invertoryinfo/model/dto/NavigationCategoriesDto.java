package mk.gov.moepp.emi.invertoryinfo.model.dto;

import java.util.List;

public class NavigationCategoriesDto {
    private int id;
    private String name;
    private String enName;
    private String prefix;
    private List<NavigationCategoriesDto> subcategories;

    public NavigationCategoriesDto(int id, String name, String enName, String prefix, List<NavigationCategoriesDto> subcategories) {
        this.id = id;
        this.name = name;
        this.enName = enName;
        this.prefix = prefix;
        this.subcategories = subcategories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setMkName(String name) {
        this.name = name;
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

    public List<NavigationCategoriesDto> getSubcategories() {
        return subcategories;
    }

    public void setAllSubcategories(List<NavigationCategoriesDto> subcategories) {
        this.subcategories = subcategories;
    }
}
