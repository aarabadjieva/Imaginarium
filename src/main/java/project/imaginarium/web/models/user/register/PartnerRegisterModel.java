package project.imaginarium.web.models.user.register;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Planet;
import project.imaginarium.data.models.users.Role;
import project.imaginarium.data.models.Sector;

import java.math.BigDecimal;

@Getter
@Setter
public class PartnerRegisterModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private Role role;
    private String name;
    private String description;
    private String logo;
    private Planet planet;
    private Sector sector;
    private String website;
    private BigDecimal price;
}
