package mk.gov.moepp.emi.invertoryinfo.model.dto;

public class CategoryDto {
    private int id;
    private String mkName;
    private String enName;
//    private String prefix;
    private int parent = -1;
    private boolean checked = false;

    public CategoryDto(int id, String mkName, String enName, int parent) {
        this.id = id;
        this.mkName = mkName;
        this.enName = enName;
        this.parent = parent;
        if (parent == -1){
            this.checked = true;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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
}
