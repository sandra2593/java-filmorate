package ru.yandex.practicum.javafilmorate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.javafilmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.javafilmorate.exception.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private int filmCounter = 0;

    @GetMapping("/films")
    public List<Film> findAll() {
        List<Film> filmsList = films.values().stream().collect(Collectors.toList());
        return filmsList;
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) {
        filmCounter++;
        film.setId(filmCounter);
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        } else if (films.containsKey(film.getId())) {
            throw new ValidationException("Такой фильм уже есть");
        } else {
            log.debug("Создан фильм.");
            films.put(film.getId(), film);
            return films.get(film.getId());
        }
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        } else if (!films.containsKey(film.getId())) {
            throw new ValidationException("Такой фильма нет в списке");
        }  else {
            log.debug("Обновлен фильм.");
            films.put(film.getId(),film);
            return films.get(film.getId());
        }
    }
}
