package project.imaginarium.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Guide extends User{

    @Column
    private BigDecimal price;

    @Column
    private String name;

    @Column(length = 5000)
    private String description;

    @Column
    private String logo;

    @Column
    @Enumerated(EnumType.STRING)
    private Planet planet;

    @Column
    @Enumerated(EnumType.STRING)
    private Sector sector;
}
