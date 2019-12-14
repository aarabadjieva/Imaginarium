package project.imaginarium.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "offers")
public class Offer extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(length = 5000)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sector sector;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Planet planet;

    @Column
    private String picture;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Partner provider;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Tag> tags;
}
