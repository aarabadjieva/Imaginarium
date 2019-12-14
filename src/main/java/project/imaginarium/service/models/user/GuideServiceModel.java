package project.imaginarium.service.models.user;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Planet;

import java.math.BigDecimal;

@Getter
@Setter
public class GuideServiceModel {

    private String username;
    private String email;
    private String name;
    private String logo;
    private Planet planet;
    private String description;
    private BigDecimal price;

}
