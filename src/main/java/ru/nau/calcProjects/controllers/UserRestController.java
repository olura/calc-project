package ru.nau.calcProjects.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nau.calcProjects.dto.UserDto;
import ru.nau.calcProjects.exception.UserExistException;
import ru.nau.calcProjects.exception.UserNotFoundException;
import ru.nau.calcProjects.models.User;
import ru.nau.calcProjects.services.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@Validated
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
    public UserDto createUser(@Valid @RequestBody User user) throws UserExistException {
        return new UserDto(userServiceImpl.addUser(user));
    }

    @PutMapping("/api/user/{id}")
    public UserDto editUser(@PathVariable("id") @Positive long id, @Valid @RequestBody User user) throws UserNotFoundException {
        return new UserDto(userServiceImpl.editUser(user, id));
    }

    @DeleteMapping("/api/user/{id}")
    public String deleteBook(@PathVariable("id") @Positive long id) {
        userServiceImpl.deleteById(id);
        return "{\"state\":\"success\"}";
    }
}
