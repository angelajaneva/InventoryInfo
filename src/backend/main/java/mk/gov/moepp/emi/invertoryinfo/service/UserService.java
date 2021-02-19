package mk.gov.moepp.emi.invertoryinfo.service;

import mk.gov.moepp.emi.invertoryinfo.model.User;
import mk.gov.moepp.emi.invertoryinfo.model.dto.UserDto;

public interface UserService {

    User createUser();

    UserDto getUser(String username);
}
