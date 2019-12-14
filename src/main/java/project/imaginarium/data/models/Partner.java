package project.imaginarium.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Partner extends User{

    @Column
    private String name;

    @Column(length = 5000)
    private String description;

    @Column
    private String logo;

    @Column
    private String website;

    @Column
    @Enumerated(EnumType.STRING)
    private Sector sector;

    @OneToMany(mappedBy = "provider")
    private List<Offer> offers;

}
