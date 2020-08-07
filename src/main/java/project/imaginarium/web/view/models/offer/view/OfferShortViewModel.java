package project.imaginarium.web.view.models.offer.view;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Planet;
import project.imaginarium.data.models.Sector;
import project.imaginarium.web.view.models.user.UserMainView;

@Getter
@Setter
public class OfferShortViewModel {

    private String name;
    private String description;
    private Planet planet;
    private String picture;
    private UserMainView provider;
    private Sector sector;

}
