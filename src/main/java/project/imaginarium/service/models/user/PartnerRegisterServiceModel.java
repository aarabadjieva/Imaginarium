package project.imaginarium.service.models.user;

import org.springframework.web.multipart.MultipartFile;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.users.Role;
import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Planet;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PartnerRegisterServiceModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private Set<Role> authorities;
    private String name;
    private String description;
    private MultipartFile logo;
    private Planet planet;
    private Sector sector;
    private String website;
    private BigDecimal price;

    public PartnerRegisterServiceModel() {
        this.authorities = new HashSet<>();
    }
}
