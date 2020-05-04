package project.imaginarium.web.view.models.user.register;

import project.imaginarium.data.models.users.Role;
import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Sector;

import java.util.Set;

@Getter
@Setter
public class ClientRegisterModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String country;
    private Set<Role> role;
    private Sector sector;
}
