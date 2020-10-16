package mk.gov.moepp.emi.invertoryinfo.model.requests;

public class AnalysisRequest {
    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public AnalysisRequest(String year) {
        this.year = year;
    }
}
