package nnpia.controllers;

import lombok.AllArgsConstructor;
import nnpia.domain.User;
import nnpia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    public UserController() {

    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Collection<User> findUsers(@RequestParam(required = false) String email) {
        if (email != null) {
            User user = userService.findByEmail(email);

            if (user == null) {
                return Collections.emptyList();
            }

            return Collections.singletonList(user);
        }
        return this.userService.findUsers();
    }

    @GetMapping("/users/{id}")
    public User findUser(@PathVariable Long id) {
        User user = userService.findUser(id);
        if (user != null) {
            return user;
        } else {
            throw new RuntimeException("User not found for ID: " + id);
        }
    }
}