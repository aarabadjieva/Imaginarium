package project.imaginarium.data.models.offers;

import project.imaginarium.data.models.users.Client;
import project.imaginarium.data.models.users.Partner;
import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.BaseEntity;
import project.imaginarium.data.models.Planet;
import project.imaginarium.data.models.Sector;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 5000)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sector sector;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Planet planet;

    @Column
    private String picture;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Partner provider;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Tag> tags;

    @ManyToMany(mappedBy = "offers")
    private List<Client> clients;
}
