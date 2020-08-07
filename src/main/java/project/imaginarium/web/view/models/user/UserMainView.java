package project.imaginarium.web.view.models.user;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.users.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserMainView {

    private String username;
    private String name;
    private String email;
    private Set<Role> authorities;
    private Sector sector;

    public boolean isAdmin(){
        List<String> roles = authorities.stream().map(Role::getAuthority).collect(Collectors.toList());
        return roles.contains("ADMIN");
    }
}
