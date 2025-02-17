package nnpia.services;

import jakarta.annotation.PostConstruct;
import nnpia.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private HashMap<Long, User> users;

    public UserService() {
    }

    @PostConstruct
    public void init() {
        users = new HashMap<>();
        users.put(1L, new User(1L, "John Doe", "john@doe.com"));
        users.put(2L, new User(2L, "Daniel Welsh", "daniel@welsh.com"));
    }

    public User findUser(Long id) {
        if (users.isEmpty()) {
            logger.warn("User map is empty!");
            return null;
        }

        return users.get(id);
    }

    public String findUsers() {
        if (users.isEmpty()) {
            logger.warn("User map is empty!");
            return "No users found.";
        }

        String userList = users.values().stream()
                .map(User::toString)
                .collect(Collectors.joining(", "));

        logger.info("Users found: {}", userList);
        return userList;
    }

    public User getUserById(Long id) {
        return users.get(id);  // Vrátí uživatele podle ID, nebo null pokud neexistuje
    }
}