package nnpia.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nnpia.domain.User;
import nnpia.dto.ErrorResponseDto;
import nnpia.dto.UserRequestDto;
import nnpia.dto.UserResponseDto;
import nnpia.exceptions.UserAlreadyExistsException;
import nnpia.exceptions.UserNotFoundException;
import nnpia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j

@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    public UserController() {

    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findUsers(@RequestParam(required = false) String email) {
        if (email != null) {
            User user = userService.findByEmail(email);

            if (user == null) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            return ResponseEntity.ok(Collections.singletonList(user));
        }
        return ResponseEntity.ok((List<User>) this.userService.findUsers());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findUser(@PathVariable String id) {
        User user = userService.findUser(id);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            throw new RuntimeException("User not found for ID: " + id);
        }
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto user) {

        log.info("Request for creating new user: {}", user);

        User createdUser = userService.createUser(new User(user.getEmail(), user.getPassword()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponseDto.builder()
                    .id(createdUser.getId())
                    .email(createdUser.getEmail())
                    .password(createdUser.getPassword()).build()
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExists(UserAlreadyExistsException ex, HttpServletRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserRequestDto userDto) {
        log.info("Request for updating user with id {}: {}", id, userDto);

        User existingUser = userService.findById(id);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        existingUser.setEmail(userDto.getEmail());
        existingUser.setPassword(userDto.getPassword());

        User updatedUser = userService.updateUser(existingUser);

        return ResponseEntity.ok(UserResponseDto.builder()
                .id(updatedUser.getId())
                .email(updatedUser.getEmail())
                .password(updatedUser.getPassword()).build()
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        log.info("Request for deleting user: {}", id);

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();

    }
}