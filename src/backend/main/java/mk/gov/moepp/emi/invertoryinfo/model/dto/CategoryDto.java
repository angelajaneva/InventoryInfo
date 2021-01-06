package mk.gov.moepp.emi.invertoryinfo.model.dto;

public class CategoryDto {
    private int id;
    private String name;
//    private String prefix;
    private int parent = -1;
    private boolean checked = false;

    public CategoryDto(int id, String name, int parent) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String mkName) {
        this.name = mkName;
    }

}
