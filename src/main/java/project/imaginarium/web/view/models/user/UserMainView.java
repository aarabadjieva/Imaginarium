package project.imaginarium.web.view.models.user;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Sector;
import project.imaginarium.service.models.user.RoleServiceModel;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserMainView {

    private String username;
    private String name;
    private String email;
    private Set<RoleServiceModel> authorities;
    private Sector sector;

    public boolean isAdmin(){
        List<String> roles = authorities.stream().map(RoleServiceModel::getAuthority).collect(Collectors.toList());
        return roles.contains("ADMIN");
    }

    public boolean isRoot(){
        List<String> roles = authorities.stream().map(RoleServiceModel::getAuthority).collect(Collectors.toList());
        return roles.contains("ROOT");
    }
}
