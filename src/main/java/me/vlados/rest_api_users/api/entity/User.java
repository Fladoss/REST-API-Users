package me.vlados.rest_api_users.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "age")
    private Integer age;

    @NotBlank
    @Column(name = "email")
    private String email;

    public User() {
    }

    public User(Integer id,
                @NotBlank String name,
                @NotNull Integer age,
                @NotBlank String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
