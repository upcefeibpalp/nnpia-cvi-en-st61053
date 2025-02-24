package nnpia.services;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nnpia.domain.User;
import nnpia.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @PostConstruct
    public void init() {
    }

    public User findUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        log.debug("Ziskan uzivatel " + user.orElse(null));

        return user.orElse(null);
    }

    public Collection<User> findUsers() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findUsersByEmail(email).orElse(null);
    }
}