package project.imaginarium.web.view.models.user.register;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import project.imaginarium.data.models.Planet;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.users.Role;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class PartnerRegisterModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private Set<Role> role;
    private String name;
    private String description;
    private MultipartFile logo;
    private Planet planet;
    private Sector sector;
    private String website;
    private BigDecimal price;
}
