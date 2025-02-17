package nnpia.controllers;

import nnpia.domain.User;
import nnpia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String findUsers() {
        return this.userService.findUsers();
    }

    @GetMapping("/user/{id}")
    public User findUser(@PathVariable Long id) {
        User user = userService.findUser(id);
        if (user != null) {
            return user;
        } else {
            throw new RuntimeException("User not found for ID: " + id);
        }
    }
}