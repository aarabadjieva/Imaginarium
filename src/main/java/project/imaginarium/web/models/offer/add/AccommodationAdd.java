package project.imaginarium.web.models.offer.add;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Planet;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.Tag;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AccommodationAdd {

    private String name;
    private String description;
    private Sector sector;
    private Planet planet;
    private String picture;
    private List<Tag> tags;
    private boolean isAllowedForAnimals;
    private BigDecimal pricePerAdult;
    private BigDecimal pricePerChildren;
}
