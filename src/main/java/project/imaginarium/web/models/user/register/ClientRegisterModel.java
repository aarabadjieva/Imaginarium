package project.imaginarium.web.models.user.register;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Role;

@Getter
@Setter
public class ClientRegisterModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String country;
    private Role role;
}
