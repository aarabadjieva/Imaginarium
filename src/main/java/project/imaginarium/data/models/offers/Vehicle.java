package project.imaginarium.data.models.offers;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Vehicle extends Offer {

    @Column
    private BigDecimal pricePerDay;
}
