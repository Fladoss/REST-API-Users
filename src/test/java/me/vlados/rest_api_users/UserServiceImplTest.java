package me.vlados.rest_api_users;

import me.vlados.rest_api_users.api.dao.UserDAOImpl;
import me.vlados.rest_api_users.api.entity.User;
import me.vlados.rest_api_users.api.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplTest {

    @Mock
    UserDAOImpl userDAO;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetUserByIdAndIsValid_thenUserShouldBeReturned() {
        User user = new User();
        user.setName("Abobius");

        Mockito.when(userDAO.getUserById(1)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1);

        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getName()).isEqualTo("Abobius");
    }

    @Test
    void whenGetUserByNameAndIsValid_thenUserShouldBeReturned() {
        User user = new User();
        user.setName("Abobius");

        Mockito.when(userDAO.getUserByName("Abobius")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserByName("Abobius");

        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getName()).isEqualTo("Abobius");
    }

    @Test
    void whenGetAllUsers_thenUserListShouldBeReturned() {
        User user1 = new User();
        user1.setName("Sglypius");
        user1.setId(1);

        User user2 = new User();
        user2.setName("Fladissius");
        user2.setId(2);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        Mockito.when(userDAO.getAllUsers()).thenReturn(userList);

        List<User> foundUserList = userService.getAllUsers();

        assertThat(foundUserList.size()).isEqualTo(2);
        assertThat(foundUserList.get(0).getName()).isEqualTo("Sglypius");
        assertThat(foundUserList.get(1).getName()).isEqualTo("Fladissius");
    }

    @Test
    void whenGetAllUsersByName_thenUserListShouldBeReturned() {
        String name = "Sglypius";

        User user1 = new User();
        user1.setName("Sglypius");
        user1.setId(1);

        User user2 = new User();
        user2.setName("Sglypius");
        user2.setId(2);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        Mockito.when(userDAO.getAllUsersByName(name)).thenReturn(userList);

        List<User> foundUserList = userService.getAllUsersByName(name);

        assertThat(foundUserList.size()).isEqualTo(2);
        assertThat(foundUserList.get(0).getName()).isEqualTo("Sglypius");
        assertThat(foundUserList.get(1).getName()).isEqualTo("Sglypius");
    }

    @Test
    void whenSaveUser_thenUserShouldBeCreated() {
        User user = new User();
        user.setName("Abobius");
        user.setId(1);

        Mockito.when(userDAO.saveUser(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertThat(savedUser.getName()).isEqualTo("Abobius");
        assertThat(savedUser.getId()).isEqualTo(1);
    }

    // do
    @Test
    void whenUpdateUser_thenUserShouldBeUpdated() {
        User user = new User();
        user.setName("Abobius");
        user.setId(1);

        Mockito.when(userDAO.saveUser(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertThat(savedUser.getName()).isEqualTo("Abobius");
        assertThat(savedUser.getId()).isEqualTo(1);
    }

    @Test
    void whenDeleteUserById_thenUserShouldBeDeleted() {
        User user = new User();
        user.setName("Abobius");
        user.setId(1);

        Mockito.when(userDAO.deleteUserById(1)).thenReturn(Optional.of(user));

        Optional<User> savedUser = userService.deleteUserById(1);

        assertThat(savedUser.isPresent()).isTrue();
        assertThat(savedUser.get().getName()).isEqualTo("Abobius");
        assertThat(savedUser.get().getId()).isEqualTo(1);
    }

    @Test
    void whenDeleteUserByName_thenUserShouldBeDeleted() {
        User user = new User();
        user.setName("Abobius");
        user.setId(1);

        Mockito.when(userDAO.deleteUserByName("Abobius")).thenReturn(Optional.of(user));

        Optional<User> deletedUser = userService.deleteUserByName("Abobius");

        assertThat(deletedUser.isPresent()).isTrue();
        assertThat(deletedUser.get().getName()).isEqualTo("Abobius");
        assertThat(deletedUser.get().getId()).isEqualTo(1);
    }
}
