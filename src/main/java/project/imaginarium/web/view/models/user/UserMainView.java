package project.imaginarium.web.view.models.user;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.users.Role;

import java.util.Set;

@Getter
@Setter
public class UserMainView {

    private String username;
    private String email;
    private Set<Role> role;
}
