package mk.gov.moepp.emi.invertoryinfo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Getter
@Table(name="user_role")

//GrantedAuthority??
public class UserRole {

    @Id
    private String name;

    private UserRole() {

    }

    public UserRole(String name) {
        this.name = name;
    }
}
