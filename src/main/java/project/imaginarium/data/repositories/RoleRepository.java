package project.imaginarium.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.imaginarium.data.models.users.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByAuthority(String name);
}
