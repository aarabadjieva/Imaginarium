package project.imaginarium.service.models.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserServiceModel {

    private String username;
    private String email;
    private Set<RoleServiceModel> authorities;
}
