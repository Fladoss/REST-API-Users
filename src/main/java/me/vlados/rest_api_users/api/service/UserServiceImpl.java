package me.vlados.rest_api_users.api.service;

import jakarta.transaction.Transactional;
import me.vlados.rest_api_users.api.dao.UserDAOImpl;
import me.vlados.rest_api_users.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDAOImpl dao;

    @Autowired
    public UserServiceImpl(UserDAOImpl dao) {
        this.dao = dao;
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return dao.getUserById(id);
    }

    @Override
    public Optional<User> getUserByName(String name) {
        return dao.getUserByName(name);
    }

    @Override
    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    @Override
    public List<User> getAllUsersByName(String name) {
        return dao.getAllUsersByName(name);
    }

    @Override
    public User saveOrUpdateUser(User user) {
        return dao.saveOrUpdateUser(user);
    }

    @Override
    public Optional<User> deleteUserById(Integer id) {
        return dao.deleteUserById(id);
    }

    @Override
    public Optional<User> deleteUserByName(String name) {
        return dao.deleteUserByName(name);
    }
}
