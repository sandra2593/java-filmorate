package ru.yandex.practicum.javafilmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.FilmService;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FilmController {

    private final String QUERY_PATH = "/films";
    private final FilmService filmService;
    private final FilmStorage filmStorage;

    @Autowired
    public FilmController(FilmService filmService, FilmStorage filmStorage) {
        this.filmService = filmService;
        this.filmStorage = filmStorage;
    }

    @GetMapping(QUERY_PATH)
    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    @PostMapping(QUERY_PATH)
    public Film create(@Valid @RequestBody Film film) {
        return filmStorage.create(film);
    }

    @PutMapping(value = QUERY_PATH)
    public Film update(@Valid @RequestBody Film film) {
        return filmStorage.update(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) { filmService.addNewLike(id, userId); }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) { filmService.deleteLike(id, userId); }

    @GetMapping("/films/popular")
    public List<Film> getTopNPopularFilms(@RequestParam(required = false) Integer count) { return filmService.getTopFilms(count); }

    @GetMapping("/films/{id}")
    public Film getUserId(@PathVariable int id) { return filmStorage.getFilmId(id); }
}
