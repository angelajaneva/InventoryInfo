package mk.gov.moepp.emi.invertoryinfo.model.dto;

import java.util.ArrayList;
import java.util.List;

public class YearDto {
    int id;
    String year;
    boolean checked;


    public YearDto(int id, String year, boolean checked) {
        this.id = id;
        this.year = year;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
