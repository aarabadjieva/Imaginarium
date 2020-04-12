package project.imaginarium.service.models.user;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.users.Role;

import java.util.Set;

@Getter
@Setter
public class UserLoggedServiceModel {

    private String username;
    private Set<Role> authorities;
    private Sector sector;
    private String logo;
}
