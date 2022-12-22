package ru.yandex.practicum.javafilmorate.exception;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(final String message) {
        super(message);
    }

}
