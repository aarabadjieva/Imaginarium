package project.imaginarium.web.view.models.offer.view;

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
public class AccommodationViewModel {

    private String name;
    private String description;
    private Planet planet;
    private String picture;
    private int days;
    private Sector sector;
    private List<Tag> tags;
    private BigDecimal pricePerAdult;
    private BigDecimal pricePerChildren;
    private Partner provider;

}
