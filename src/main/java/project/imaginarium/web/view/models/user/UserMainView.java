package project.imaginarium.web.view.models.user;

import project.imaginarium.data.models.users.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserMainView {

    private String username;
    private String email;
    private Set<Role> role;
}
