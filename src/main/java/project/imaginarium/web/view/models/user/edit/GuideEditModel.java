package project.imaginarium.web.view.models.user.edit;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Planet;

import java.math.BigDecimal;

@Getter
@Setter
public class GuideEditModel {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String name;
    private String description;
    private BigDecimal price;
    private Planet planet;
}
