package mk.gov.moepp.emi.invertoryinfo.web;

import mk.gov.moepp.emi.invertoryinfo.model.dto.UserDto;
import mk.gov.moepp.emi.invertoryinfo.service.UserService;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/user", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto getUser(@RequestBody UserDto username){
        return userService.getUser(username.getUsername());
    }
}
