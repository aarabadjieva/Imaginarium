package project.imaginarium.data.models.users;

import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.Planet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Guide extends User {

    @Column
    private BigDecimal price;

    @Column(length = 5000)
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private Planet planet;


}
