package mk.gov.moepp.emi.invertoryinfo.model.requests;

public class GasRequest {
    private String name;

    public GasRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
