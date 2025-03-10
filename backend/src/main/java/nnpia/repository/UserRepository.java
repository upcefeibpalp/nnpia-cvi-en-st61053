package nnpia.repository;

import lombok.NonNull;
import nnpia.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    // Optional if return one object
    Optional<User> findUsersByEmail(String email);

    boolean existsByEmail(@NonNull String email);

    User findByEmail(String email);
}
