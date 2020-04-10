package project.imaginarium.service.models.user;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.users.Role;
import project.imaginarium.data.models.Sector;

@Getter
@Setter
public class UserLoggedServiceModel {

    private String username;
    private Role role;
    private Sector sector;
    private String logo;
}
