package project.imaginarium.web.view.models.user.view;

import project.imaginarium.data.models.offers.Offer;
import lombok.Getter;
import lombok.Setter;
import project.imaginarium.web.view.models.user.UserMainView;

import java.util.List;

@Getter
@Setter
public class ClientViewModel extends UserMainView {

    private String password;
    private String country;
    private String logo;
    private List<Offer> offers;

}
