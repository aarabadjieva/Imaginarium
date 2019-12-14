package project.imaginarium.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Accommodation extends Offer {

    @Column(nullable = false)
    private boolean isAllowedForAnimals;

    @Column(nullable = false)
    @Min(value = 0)
    private BigDecimal pricePerAdult;

    @Column
    @Min(value = 0)
    private BigDecimal pricePerChildren;

    @Column
    @Min(value = 0)
    private int days;
}
