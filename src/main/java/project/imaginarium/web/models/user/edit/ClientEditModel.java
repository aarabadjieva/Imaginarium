package project.imaginarium.web.models.user.edit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientEditModel {

    private String username;
    private String email;
    private String country;
    private String password;
    private String confirmPassword;
}
