package project.imaginarium.service.models.user;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.users.Role;

import java.util.Set;

@Getter
@Setter
public class ClientRegisterServiceModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private Set<Role> authorities;
    private String country;
    private String logo;
}
