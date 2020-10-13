package mk.gov.moepp.emi.invertoryinfo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    @ManyToOne
    private UserRole userRole;

    private Boolean active = true;

    public static User createNewUser(String username, String password, UserRole role) {
        User user = new User();
        user.active = true;
        user.username=username;
        user.password = password;
        user.userRole = role;
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userRole==null) {
            return Collections.emptyList();
        }
        List<GrantedAuthority> auths = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(this.userRole.getName());
        auths.add(authority);
        return auths;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
