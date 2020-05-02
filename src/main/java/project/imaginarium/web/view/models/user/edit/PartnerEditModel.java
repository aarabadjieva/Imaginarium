package project.imaginarium.web.view.models.user.edit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerEditModel {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String name;
    private String description;
    private String logo;
    private String website;
}
