package mk.gov.moepp.emi.invertoryinfo.service.impl;

import mk.gov.moepp.emi.invertoryinfo.model.User;
import mk.gov.moepp.emi.invertoryinfo.model.UserRole;
import mk.gov.moepp.emi.invertoryinfo.repository.UserRepository;
import mk.gov.moepp.emi.invertoryinfo.repository.UserRoleRepository;
import mk.gov.moepp.emi.invertoryinfo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    @EventListener(ApplicationReadyEvent.class)
    @PostConstruct
    @Override
    public User createUser() {
        UserRole role = new UserRole("ROLE_ADMIN");
        if (userRoleRepository.count() == 0) {
            userRoleRepository.saveAndFlush(role);
        }

        User user = User.createNewUser("admin@test.com", passwordEncoder.encode("123"),
                List.of(userRoleRepository.findByName("ROLE_ADMIN").orElseThrow(RuntimeException::new)));

        if (userRepository.count() == 0 )
            userRepository.save(user);

        return user;
    }
}
