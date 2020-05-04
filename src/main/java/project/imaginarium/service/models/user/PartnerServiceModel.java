package project.imaginarium.service.models.user;

import project.imaginarium.data.models.offers.Offer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PartnerServiceModel {

    private String username;
    private String email;
    private String name;
    private String logo;
    private String website;
    private String description;
    private List<Offer> offers;
}
