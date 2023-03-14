package ru.yandex.practicum.javafilmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.controller.FilmController;
import ru.yandex.practicum.javafilmorate.exception.EnumOfExceptions;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.exception.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage{
    private final HashMap<Integer, Film> films = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private int filmCounter = 0;

    private final LocalDate EARLIEST_RELEASE_DATE = LocalDate.of(1895,12,28);

    public List<Film> findAll() {
        List<Film> filmsList = films.values().stream().collect(Collectors.toList());
        return filmsList;
    }

    public Film create(Film film) {
        filmCounter++;
        film.setId(filmCounter);
        if (film.getReleaseDate().isBefore(EARLIEST_RELEASE_DATE)) {
            filmCounter--;
            throw new ValidationException(EnumOfExceptions.RELEASE_DATE.getExp());
        } else if (films.containsKey(film.getId())) {
            filmCounter--;
            throw new ValidationException(EnumOfExceptions.DOUBLE_FILM.getExp());
        } else {
            log.debug("Создан фильм.");
            films.put(film.getId(), film);
            return films.get(film.getId());
        }
    }

    public Film update(Film film) {
        if (film.getReleaseDate().isBefore(EARLIEST_RELEASE_DATE)) {
            throw new ValidationException(EnumOfExceptions.RELEASE_DATE.getExp());
        } else if (!films.containsKey(film.getId())) {
            throw new NotFoundException(EnumOfExceptions.NO_FILM.getExp());
        }  else {
            log.debug("Обновлен фильм.");
            films.put(film.getId(),film);
            return films.get(film.getId());
        }
    }

    public Film getFilmId(int id) {
        if (Objects.isNull(films.get(id))) {
            throw new NotFoundException(EnumOfExceptions.NO_FILM.getExp());
        }
        return films.get(id);
    }
}
