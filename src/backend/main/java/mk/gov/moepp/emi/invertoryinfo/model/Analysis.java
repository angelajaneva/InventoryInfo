package mk.gov.moepp.emi.invertoryinfo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.Year;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "analysis")
@Where(clause = "deleted=false")
public class Analysis {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String year;

    @Column(name = "deleted")
    private boolean deleted = false;

    @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AnalysisCategoryGas> categoryGases;

    public int getId() {
        return id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
