package project.imaginarium.data.models.users;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.offers.Offer;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Client extends User {

    @Column
    @NotEmpty
    private String country;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "clients_offers",
            joinColumns = {@JoinColumn(name = "client_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "offer_id", referencedColumnName = "id")})
    private List<Offer> offers;

    public Client() {
        offers = new ArrayList<>();
    }
}
