package project.imaginarium.web.api.models.user.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.offers.Offer;
import project.imaginarium.web.view.models.user.UserMainView;

import java.util.List;

@Getter
@Setter
public class PartnerResponseModel extends UserMainView {

    private String logo;
    private String website;
    private String description;
    @JsonIgnore
    private List<Offer> offers;

}
