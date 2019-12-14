package project.imaginarium.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class TimeTravel extends Offer {

    @Column
    private int year;

    @Column
    private int ageRestrictionMin;

    @Column
    @Min(0)
    private BigDecimal pricePerAdult;

    @Column
    @Min(0)
    private BigDecimal pricePerChildren;

}
