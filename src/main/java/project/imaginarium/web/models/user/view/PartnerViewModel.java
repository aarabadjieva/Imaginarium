package project.imaginarium.web.models.user.view;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.offers.Offer;

import java.util.List;

@Getter
@Setter
public class PartnerViewModel {

    private String username;
    private String email;
    private String name;
    private String logo;
    private String website;
    private String description;
    private List<Offer> offers;
}
