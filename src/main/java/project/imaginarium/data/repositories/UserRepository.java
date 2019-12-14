package project.imaginarium.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.imaginarium.data.models.Role;
import project.imaginarium.data.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsernameAndPassword(String username, String password);

    List<User> findAllByRole(Role role);

    User findByUsername(String name);

}
