package ru.nau.calcProjects.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nau.calcProjects.dto.CalculationDto;
import ru.nau.calcProjects.dto.UserDto;
import ru.nau.calcProjects.exception.UserExistException;
import ru.nau.calcProjects.exception.UserNotFoundException;
import ru.nau.calcProjects.exception.ValidateException;
import ru.nau.calcProjects.models.User;
import ru.nau.calcProjects.services.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserRestController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserRestController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/api/user")
    List<UserDto> getAllUser() {
        return userServiceImpl.findAllUser()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }
    
    @PostMapping("/api/user")
    public UserDto createUser(@RequestBody User user) throws UserExistException, ValidateException {
        if (user.getUsername().isEmpty()) {
            throw new ValidateException("Имя пользователя не может быть пустым. Необходимо заполнить.");
        } else if (user.getPassword().isEmpty()) {
            throw new ValidateException("Пароль не может быть пустым. Необходимо заполнить.");
        }
        return new UserDto(userServiceImpl.addUser(user));
    }

    @PutMapping("/api/user/{id}")
    public UserDto editUser(@PathVariable("id") long id, @RequestBody User user) throws UserNotFoundException {
        return new UserDto(userServiceImpl.editUser(user, id));
    }

    @DeleteMapping("/api/user/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        userServiceImpl.deleteById(id);
        return "{\"state\":\"success\"}";
    }
}
