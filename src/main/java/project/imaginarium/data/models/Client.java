package project.imaginarium.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Client extends User{

    @Column
    private String country;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "clients_offers",
            joinColumns = {@JoinColumn(name = "client_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "offer_id", referencedColumnName = "id")})
    private List<Offer> offers;
}
