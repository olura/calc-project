package ru.nau.calcProjects.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.nau.calcProjects.models.User;
import ru.nau.calcProjects.services.UserServiceImpl;

@Component
public class UserValidator implements Validator {

    private final UserServiceImpl userService;

    @Autowired
    public UserValidator(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.findUserByUsername(user.getUsername()).isPresent()) {
            errors.rejectValue("username", String.valueOf(HttpStatus.CONFLICT), "Пользователь с таким именем уже существует");
        }
    }
}
