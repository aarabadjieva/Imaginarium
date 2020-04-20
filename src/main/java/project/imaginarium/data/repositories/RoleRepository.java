package project.imaginarium.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.imaginarium.data.models.users.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByAuthority(String name);
}
