package me.vlados.rest_api_test.api.dao;

import me.vlados.rest_api_test.api.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    Optional<User> getUserById(Integer id);

    Optional<User> getUserByName(String name);

    List<User> getAllUsers();

    List<User> getAllUsersByName(String name);

    User saveUser(User user);

    Optional<User> deleteUserById(Integer id);

    Optional<User> deleteUserByName(String name);
}
