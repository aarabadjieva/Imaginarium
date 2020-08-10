package project.imaginarium.service.models.user;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Sector;

import java.util.Set;

@Getter
@Setter
public class UserServiceModel {

    private String username;
    private String name;
    private String email;
    private Set<RoleServiceModel> authorities;
    private Sector sector;
}
