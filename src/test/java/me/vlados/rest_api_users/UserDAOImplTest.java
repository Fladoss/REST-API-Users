package me.vlados.rest_api_users;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import me.vlados.rest_api_users.api.dao.UserDAOImpl;
import me.vlados.rest_api_users.api.entity.User;
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

public class UserDAOImplTest {
    @Mock
    EntityManager entityManager;

    @InjectMocks
    UserDAOImpl userDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetUserByIdAndIsValid_thenUserShouldBeReturned() {
        User user = new User();
        user.setName("Abobius");
        user.setId(1);

        Mockito.when(entityManager.find(User.class, 1)).thenReturn(user);

        Optional<User> foundUser = userDAO.getUserById(1);

        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getName()).isEqualTo("Abobius");
        assertThat(foundUser.get().getId()).isEqualTo(1);
    }

    @Test
    void whenGetUserByNameAndIsValid_thenUserShouldBeReturned() {
        User user = new User();
        user.setName("Abobius");
        user.setId(1);

        List<User> userList = new ArrayList<>();
        userList.add(user);

        Query query = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery("FROM User WHERE name =: name"))
                .thenReturn(query);
        Mockito.when(query.setParameter("name", user.getName()))
                .thenReturn(query);
        Mockito.when(query.getResultList())
                .thenReturn(userList);

        Optional<User> foundUser = userDAO.getUserByName("Abobius");

        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getId()).isEqualTo(1);
        assertThat(foundUser.get().getName()).isEqualTo("Abobius");
    }

    @Test
    public void whenGetAllUsers_thenUserListShouldBeReturned() {
        User user1 = new User();
        user1.setName("Sglypius");
        user1.setId(1);

        User user2 = new User();
        user2.setName("Fladissius");
        user2.setId(2);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        Query query = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery("FROM User"))
                .thenReturn(query);
        Mockito.when(query.getResultList())
                .thenReturn(userList);


        List<User> foundUserList = userDAO.getAllUsers();

        assertThat(foundUserList.size()).isEqualTo(2);
        assertThat(foundUserList.get(0).getName()).isEqualTo("Sglypius");
        assertThat(foundUserList.get(1).getName()).isEqualTo("Fladissius");
    }

    @Test
    public void whenGetAllUsersByName_thenUserListShouldBeReturned() {
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

        Query query = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery("FROM User WHERE name =: name"))
                .thenReturn(query);
        Mockito.when(query.setParameter("name", name))
                .thenReturn(query);
        Mockito.when(query.getResultList())
                .thenReturn(userList);


        List<User> foundUserList = userDAO.getAllUsersByName(name);

        assertThat(foundUserList.size()).isEqualTo(2);
        assertThat(foundUserList.get(0).getName()).isEqualTo("Sglypius");
        assertThat(foundUserList.get(1).getName()).isEqualTo("Sglypius");
    }

    @Test
    void whenSaveUser_thenUserShouldBeCreatedOrUpdated() {
        User user = new User();
        user.setName("Abobius");

        userDAO.saveUser(user);

        Mockito.verify(entityManager).merge(user);
    }

    @Test
    void whenDeleteUserById_thenUserShouldBeDeleted() {
        User user = new User();
        user.setId(10);

        Mockito.when(entityManager.find(User.class, 10)).thenReturn(user);

        userDAO.deleteUserById(10);

        Mockito.verify(entityManager).remove(user);
    }

    @Test
    void whenDeleteUserByName_thenUserShouldBeDeleted() {
        User user = new User();
        user.setName("Abobius");

        List<User> userList = new ArrayList<>();
        userList.add(user);

        Query query = Mockito.mock(Query.class);

        System.out.println(query);

        Mockito.when(entityManager.createQuery("FROM User WHERE name =: name"))
                .thenReturn(query);
        Mockito.when(query.setParameter("name", user.getName()))
                .thenReturn(query);
        Mockito.when(query.getResultList())
                .thenReturn(userList);

//                .setParameter("name", user.getName())
//                .getResultList()).thenReturn(userList);

        userDAO.deleteUserByName("Abobius");

        Mockito.verify(entityManager).remove(user);
    }

}