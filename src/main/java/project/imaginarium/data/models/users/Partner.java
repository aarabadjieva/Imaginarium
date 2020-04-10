package project.imaginarium.data.models.users;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.offers.Offer;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Entity
public class Partner extends User {

    @Column
    @NotEmpty
    @Length(min = 3)
    private String name;

    @Column(length = 5000)
    @Length(min = 3)
    private String description;

    @Column
    private String logo;

    @Column
    private String website;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sector sector;

    @OneToMany(mappedBy = "provider")
    private List<Offer> offers;

}
