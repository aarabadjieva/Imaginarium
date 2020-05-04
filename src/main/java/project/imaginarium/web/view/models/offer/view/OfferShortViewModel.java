package project.imaginarium.web.view.models.offer.view;

import project.imaginarium.data.models.Planet;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.users.Partner;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferShortViewModel {

    private String name;
    private String description;
    private Planet planet;
    private String picture;
    private Partner provider;
    private Sector sector;

}
