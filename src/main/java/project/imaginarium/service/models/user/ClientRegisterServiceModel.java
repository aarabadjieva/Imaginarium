package project.imaginarium.service.models.user;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Role;

@Getter
@Setter
public class ClientRegisterServiceModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private Role role;
    private String country;
}
