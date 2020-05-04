package project.imaginarium.web.view.models.user.view;

import project.imaginarium.data.models.offers.Offer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientViewModel {

    private String username;
    private String email;
    private String password;
    private String country;
    private List<Offer> offers;
}
