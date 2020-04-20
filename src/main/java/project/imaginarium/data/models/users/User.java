package project.imaginarium.data.models.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.imaginarium.data.models.BaseEntity;
import project.imaginarium.data.models.Sector;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private Sector sector;

    @Column
    private String logo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> authorities;

    public User() {
        this.authorities = new HashSet<>();
    }
}
