package me.vlados.rest_api_test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.vlados.rest_api_test.api.controller.UserController;
import me.vlados.rest_api_test.api.model.User;
import me.vlados.rest_api_test.api.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserServiceImpl userService;

    @Test
    void whenGetUserByIdAndIsValid_thenUserShouldBeReturned() {
        User user = new User();
        user.setId(10);

        Mockito.when(userService.getUserById(10)).thenReturn(Optional.of(user));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/api/v1/user/10")
                            .contentType("application/json"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(10));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Mockito.verify(userService).getUserById(10);
        assertThat(userService.getUserById(10).isPresent()).isTrue();
        assertThat(userService.getUserById(10).get()).isEqualTo(user);
    }

    @Test
    void whenGetUserByNameAndIsValid_thenUserShouldBeReturned() {
        User user = new User();
        user.setName("Abobius");

        Mockito.when(userService.getUserByName("Abobius")).thenReturn(Optional.of(user));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/api/v1/user/Abobius")
                            .contentType("application/json"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Abobius"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Mockito.verify(userService).getUserByName("Abobius");
        assertThat(userService.getUserByName("Abobius").isPresent()).isTrue();
        assertThat(userService.getUserByName("Abobius").get()).isEqualTo(user);
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

        Mockito.when(userService.getAllUsers()).thenReturn(userList);

        try {
            mvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Sglypius"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Fladissius"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Mockito.verify(userService).getAllUsers();
        assertThat(userService.getAllUsers()).isNotNull();
        assertThat(userService.getAllUsers()).isEqualTo(userList);
    }

    @Test
    void whenGetAllUsersByName_thenUserListShouldBeReturned() {
        User user1 = new User();
        user1.setName("Sglypius");
        user1.setId(1);

        User user2 = new User();
        user2.setName("Sglypius");
        user2.setId(2);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        Mockito.when(userService.getAllUsersByName("Sglypius")).thenReturn(userList);

        try {
            mvc.perform(MockMvcRequestBuilders.get("/api/v1/users/Sglypius"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Sglypius"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Sglypius"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Mockito.verify(userService).getAllUsersByName("Sglypius");
        assertThat(userService.getAllUsers()).isNotNull();
        assertThat(userService.getAllUsersByName("Sglypius")).isEqualTo(userList);
    }

    @Test
    void whenSaveUser_thenUserShouldBeCreatedOrUpdated() {
        User user = new User();
        //user.setId(100);
        user.setName("Abobius");
        user.setAge(23);
        user.setEmail("abob@gmail.com");

        Mockito.when(userService.saveUser(user)).thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String userJson;

        try {
            userJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            mvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                            .contentType("application/json")
                            .content(userJson))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Mockito.verify(userService).saveUser(user);
        assertThat(userService.saveUser(user)).isEqualTo(user);
    }

    @Test
    void whenDeleteUserById_thenUserShouldBeDeleted() {
        User user = new User();
        user.setId(10);

        Mockito.when(userService.deleteUserById(10)).thenReturn(Optional.of(user));

        try {
            mvc.perform(MockMvcRequestBuilders.delete("/api/v1/user/10")
                            .contentType("application/json"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(10));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Mockito.verify(userService).deleteUserById(10);
        assertThat(userService.deleteUserById(10).isPresent()).isTrue();
        assertThat(userService.deleteUserById(10).get()).isEqualTo(user);
    }

    @Test
    void whenDeleteUserByName_thenUserShouldBeDeleted() {
        User user = new User();
        user.setName("Abobius");

        Mockito.when(userService.deleteUserByName("Abobius")).thenReturn(Optional.of(user));

        try {
            mvc.perform(MockMvcRequestBuilders.delete("/api/v1/user/Abobius")
                            .contentType("application/json"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Abobius"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Mockito.verify(userService).deleteUserByName("Abobius");
        assertThat(userService.deleteUserByName("Abobius").isPresent()).isTrue();
        assertThat(userService.deleteUserByName("Abobius").get()).isEqualTo(user);
    }
}
