package project.imaginarium.data.models.offers;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Event extends Offer {

    @Column
    private int year;

    @Column
    private int ageRestrictionMin;

    @Column
    private BigDecimal pricePerAdult;

    @Column
    private BigDecimal pricePerChildren;

}
