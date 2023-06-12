package me.vlados.rest_api_test.api.controller;

import jakarta.validation.Valid;
import me.vlados.rest_api_test.api.model.User;
import me.vlados.rest_api_test.exceptions.NoSuchUserException;
import me.vlados.rest_api_test.exceptions.NoUsersFoundException;
import me.vlados.rest_api_test.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // using regex to match only digits
    @GetMapping("/user/{id:\\d+}")
    public User getUser(@PathVariable Integer id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new NoSuchUserException("User with 'id' " + id + " not found!"));
    }

    // using regex to match only letters
    @GetMapping("/user/{name:[a-zA-Z ]+}")
    public User getUser(@PathVariable String name) {
        return userService.getUserByName(name)
                .orElseThrow(() -> new NoSuchUserException("User with 'name' " + name + " not found!"));
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();

        if (!users.isEmpty()) {
            return users;
        } else {
            throw new NoUsersFoundException("No users found!");
        }
    }

    // using regex to match only letters
    @GetMapping("/users/{name:[a-zA-Z ]+}")
    public List<User> getAllUsersByName(@PathVariable String name) {
        List<User> users = userService.getAllUsersByName(name);

        if (!users.isEmpty()) {
            return users;
        } else {
            throw new NoUsersFoundException("No users found with 'name' " + name + "!");
        }
    }


    @PostMapping("/user")
    public User addUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/user")
    public User updateUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    // using regex to match only digits
    @DeleteMapping("/user/{id:\\d+}")
    public User deleteUser(@PathVariable Integer id) {
        return userService.deleteUserById(id)
                .orElseThrow(() -> new NoSuchUserException("User with 'id' " + id + " not found!"));
    }

    // using regex to match only letters
    @DeleteMapping("/user/{name:[a-zA-Z ]+}")
    public User deleteUser(@PathVariable String name) {
        return userService.deleteUserByName(name)
                .orElseThrow(() -> new NoSuchUserException("User with 'name' " + name + " not found!"));
    }
}