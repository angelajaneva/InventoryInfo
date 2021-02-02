package mk.gov.moepp.emi.invertoryinfo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "year")
//@Where(clause = "deleted=false")
public class Year {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String year;

//    @Column(name = "deleted")
//    private boolean deleted = false;

    @OneToMany(mappedBy = "year", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Analysis> analyses;

    public int getId() {
        return id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

//    public boolean isDeleted() {
//        return deleted;
//    }
//
//    public void setDeleted(boolean deleted) {
//        this.deleted = deleted;
//    }
}
