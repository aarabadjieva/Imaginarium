package project.imaginarium.data.models.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.imaginarium.data.models.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Role extends BaseEntity {

    @Column
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;

    public Role(String authority) {
        this.authority = authority;
    }

}
