package mk.gov.moepp.emi.invertoryinfo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "analysis", indexes = {
        @Index(name = "IX_Analysis_Category", columnList = "year_id,category_id")
})
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
//    @JoinColumn(name = "fk_analysis", insertable = false, updatable = false)
    @JsonManagedReference
    private Year year;

    @ManyToOne
//    @JoinColumn(name = "fk_category", insertable = false, updatable = false)
    @JsonManagedReference
    private Category category;

    @ManyToOne
//    @JoinColumn(name = "fk_gas", insertable = false, updatable = false)
    @JsonManagedReference
    private Gas gas;

    private double concentrate;

    public int getId(){
        return id;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Gas getGas() {
        return gas;
    }

    public void setGas(Gas gas) {
        this.gas = gas;
    }

    public double getConcentrate() {
        return concentrate;
    }

    public void setConcentrate(double concentrate) {
        this.concentrate = concentrate;
    }
}
