package project.imaginarium.web.view.models.user.edit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PartnerEditModel {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String name;
    private String description;
    private MultipartFile logo;
    private String website;
}
