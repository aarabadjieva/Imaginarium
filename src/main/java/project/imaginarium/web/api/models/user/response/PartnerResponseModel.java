package project.imaginarium.web.api.models.user.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.offers.Offer;

import java.util.List;

@Getter
@Setter
public class PartnerResponseModel {

    private String username;
    private String email;
    private String name;
    private String logo;
    private String website;
    private String description;
    private Sector sector;
    @JsonIgnore
    private List<Offer> offers;
}
