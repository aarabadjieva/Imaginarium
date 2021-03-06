package project.imaginarium.service.models.offer;

import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.offers.Tag;
import project.imaginarium.data.models.users.Partner;
import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Planet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AccommodationServiceModel {

    private String name;
    private String description;
    private int days;
    private Sector sector;
    private Planet planet;
    private String picture;
    private List<Tag> tags;
    private BigDecimal pricePerAdult;
    private BigDecimal pricePerChildren;
    private Partner provider;

    public AccommodationServiceModel() {
        this.tags = new ArrayList<>();
    }
}
