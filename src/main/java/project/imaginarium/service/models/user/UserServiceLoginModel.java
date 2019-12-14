package project.imaginarium.service.models.user;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Role;

@Getter
@Setter
public class UserServiceLoginModel {

    private String username;
    private String password;
    private Role role;
}
