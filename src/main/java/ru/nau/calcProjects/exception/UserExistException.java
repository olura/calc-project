package ru.nau.calcProjects.exception;

public class UserExistException extends Exception {
    public UserExistException(String message) {
        super(message);
    }
}
