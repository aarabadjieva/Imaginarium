package project.imaginarium.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Vehicle extends Offer {

    @Column
    @NotEmpty
    @Min(0)
    private BigDecimal pricePerDay;
}
