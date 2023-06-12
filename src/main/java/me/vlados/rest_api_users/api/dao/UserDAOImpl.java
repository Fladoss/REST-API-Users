package me.vlados.rest_api_users.api.dao;

import jakarta.persistence.EntityManager;
import me.vlados.rest_api_users.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    private final EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        User user = entityManager.find(User.class, id);

        if (user != null) {
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    // returns the first entry (user) with the given name
    @Override
    public Optional<User> getUserByName(String name) {
        // we get a list of users by name, but actually only one will be returned
        List<User> dummyUser = entityManager.createQuery("FROM User WHERE name =: name")
                .setParameter("name", name)
                .getResultList();

        // returns the optional containing user or empty optional
        return dummyUser.stream().findFirst();
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) entityManager.createQuery("FROM User")
                .getResultList();
    }

    @Override
    public List<User> getAllUsersByName(String name) {
        return (List<User>) entityManager.createQuery("FROM User WHERE name =: name")
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public User saveUser(User user) {
        return entityManager.merge(user);
    }

    @Override
    public Optional<User> deleteUserById(Integer id) {
        Optional<User> user = getUserById(id);

        user.ifPresent(entityManager::remove);

        return user;
    }

    @Override
    public Optional<User> deleteUserByName(String name) {
        Optional<User> user = getUserByName(name);

        user.ifPresent(entityManager::remove);

        return user;
    }
}
