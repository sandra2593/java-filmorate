package ru.yandex.practicum.javafilmorate.exception;

public class ValidationException  extends RuntimeException {

    public ValidationException(final String message) {
        super(message);
    }

}
