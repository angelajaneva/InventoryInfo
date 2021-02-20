package mk.gov.moepp.emi.invertoryinfo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Gas {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(mappedBy = "gas", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Analysis> analysisCategory;
    //Getters and Setters (nesto ne rabote lombok)

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
