package project.imaginarium.data.repositories;

import project.imaginarium.data.models.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import project.imaginarium.data.models.users.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsernameAndPassword(String username, String password);

    List<User> findAllByAuthoritiesContaining(Role role);

    Optional<User> findByUsername(String name);

}
