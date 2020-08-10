package project.imaginarium.service.models.user;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.offers.Offer;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ClientServiceModel {

    private String username;
    private String email;
    private String name;
    private String password;
    private String country;
    private List<Offer> offers;
    private Sector sector;
    private String logo;
    private Set<RoleServiceModel> authorities;
}
