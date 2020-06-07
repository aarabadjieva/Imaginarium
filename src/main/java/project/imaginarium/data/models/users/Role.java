package project.imaginarium.data.models.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import project.imaginarium.data.models.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

    @Column
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;

    public Role(String authority) {
        this.authority = authority;
    }

}
