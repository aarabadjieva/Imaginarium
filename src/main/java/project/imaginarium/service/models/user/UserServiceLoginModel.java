package project.imaginarium.service.models.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.users.Role;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceLoginModel {

    private String username;
    private String password;
    private Set<Role> authorities;
    private Sector sector;

}
