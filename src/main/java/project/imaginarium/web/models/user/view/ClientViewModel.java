package project.imaginarium.web.models.user.view;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Offer;

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
