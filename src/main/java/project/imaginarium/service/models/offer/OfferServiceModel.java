package project.imaginarium.service.models.offer;

import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.offers.Tag;
import project.imaginarium.data.models.users.Partner;
import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Planet;

import java.util.List;

@Getter
@Setter
public class OfferServiceModel {

    private String name;
    private String description;
    private Sector sector;
    private Planet planet;
    private String picture;
    private Partner provider;
    private List<Tag> tags;
}
