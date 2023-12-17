package ru.nau.calcProjects.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nau.calcProjects.models.Role;
import ru.nau.calcProjects.models.User;

@NoArgsConstructor
@Data
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private Role role;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}