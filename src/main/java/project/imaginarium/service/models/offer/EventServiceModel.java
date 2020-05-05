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
public class EventServiceModel {

    private String name;
    private String description;
    private Sector sector;
    private Planet planet;
    private String picture;
    private List<Tag> tags;
    private BigDecimal pricePerAdult;
    private BigDecimal pricePerChildren;
    private int year;
    private int ageRestrictionMin;
    private Partner provider;



    public EventServiceModel() {
        this.tags = new ArrayList<>();
    }
}
