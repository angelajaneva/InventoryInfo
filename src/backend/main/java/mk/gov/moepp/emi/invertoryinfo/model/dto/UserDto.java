package mk.gov.moepp.emi.invertoryinfo.model.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDto {

    String username;
    Collection<? extends GrantedAuthority> role;

    public UserDto(String username, Collection<? extends GrantedAuthority> role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<? extends GrantedAuthority> getRole() {
        return role;
    }

    public void setRole(Collection<? extends GrantedAuthority> role) {
        this.role = role;
    }
}
