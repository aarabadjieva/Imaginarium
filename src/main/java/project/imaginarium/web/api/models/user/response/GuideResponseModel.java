package project.imaginarium.web.api.models.user.response;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Planet;
import project.imaginarium.data.models.Sector;

import java.math.BigDecimal;

@Getter
@Setter
public class GuideResponseModel {

    private String username;
    private String email;
    private String name;
    private String logo;
    private Planet planet;
    private String description;
    private BigDecimal price;
    private Sector sector;
}
