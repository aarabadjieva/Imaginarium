package project.imaginarium.data.models.users;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import project.imaginarium.data.models.offers.Offer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class Partner extends User {


    @Column(length = 5000)
    @Length(min = 3)
    private String description;


    @Column
    private String website;


    @OneToMany(mappedBy = "provider")
    private List<Offer> offers;

}
