package ru.yandex.practicum.javafilmorate.exception;

public class NotFoundException   extends RuntimeException {

    public NotFoundException(final String message) {
        super(message);
    }

}
