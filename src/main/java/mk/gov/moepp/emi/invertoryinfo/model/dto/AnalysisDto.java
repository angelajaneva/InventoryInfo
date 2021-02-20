package mk.gov.moepp.emi.invertoryinfo.model.dto;

public class AnalysisDto {

    private String year;
    private String gasName;
    private CategoryDto category;
    private double concentrate;

    public AnalysisDto(String year, String gasName, CategoryDto category, double concentrate) {
        this.year = year;
        this.gasName = gasName;
        this.category = category;
        this.concentrate = concentrate;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGasName() {
        return gasName;
    }

    public void setGasName(String gasName) {
        this.gasName = gasName;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public double getConcentrate() {
        return concentrate;
    }

    public void setConcentrate(double concentrate) {
        this.concentrate = concentrate;
    }
}
