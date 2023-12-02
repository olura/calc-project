package ru.nau.calcProjects.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "service_users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 25, message = "Имя должно содержать от 3 до 30 символов")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")

    private String password;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Неверный формат email")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}