package ru.yandex.practicum.javafilmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.javafilmorate.controller.FilmController;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.exception.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage{
    private final HashMap<Integer, Film> films = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private int filmCounter = 0;

    public List<Film> findAll() {
        List<Film> filmsList = films.values().stream().collect(Collectors.toList());
        return filmsList;
    }

    public Film create(Film film) {
        filmCounter++;
        film.setId(filmCounter);
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            filmCounter--;
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        } else if (films.containsKey(film.getId())) {
            filmCounter--;
            throw new ValidationException("Такой фильм уже есть");
        } else {
            log.debug("Создан фильм.");
            films.put(film.getId(), film);
            return films.get(film.getId());
        }
    }

    public Film update(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        } else if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Такой фильма нет в списке");
        }  else {
            log.debug("Обновлен фильм.");
            films.put(film.getId(),film);
            return films.get(film.getId());
        }
    }

    public Film getFilmId(int id) {
        if (films.get(id) == null) {
            throw new NotFoundException("Фильм не найден");
        }
        return films.get(id);
    }
}
