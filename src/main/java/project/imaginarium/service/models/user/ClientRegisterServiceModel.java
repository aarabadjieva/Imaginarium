package project.imaginarium.service.models.user;

import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.users.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ClientRegisterServiceModel {

    private String username;
    private String password;
    private String name;
    private String confirmPassword;
    private String email;
    private Set<Role> authorities;
    private String country;
    private String logo;
    private Sector sector;

    public ClientRegisterServiceModel() {
        authorities = new HashSet<>();
        name = username;
    }
}
