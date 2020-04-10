package project.imaginarium.web.models.offer.view;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Planet;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.offers.Tag;
import project.imaginarium.data.models.users.Partner;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class VehicleViewModel {

    private String name;
    private String description;
    private Sector sector;
    private Planet planet;
    private String picture;
    private List<Tag> tags;
    private BigDecimal pricePerDay;
    private Partner provider;

}
