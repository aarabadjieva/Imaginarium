package project.imaginarium.service.models.user;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Planet;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.users.Role;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PartnerRegisterServiceModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private Set<Role> authorities;
    private String name;
    private String description;
    private String logo;
    private Planet planet;
    private Sector sector;
    private String website;

    public PartnerRegisterServiceModel() {
        this.authorities = new HashSet<>();
    }
}
