package nnpia.repository;

import nnpia.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Optional if return one object
    Optional<User> findUsersByEmail(String email);
}
