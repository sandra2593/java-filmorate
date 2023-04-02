package ru.yandex.practicum.javafilmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;
import ru.yandex.practicum.javafilmorate.storage.LikesStorage;

import javax.validation.Valid;
import java.util.Collection;

@RestController
public class FilmController {

    private final FilmStorage filmStorage;
    private final LikesStorage likesStorage;

    @Autowired
    public FilmController(LikesStorage likesStorage, @Qualifier("DbFilmStorage") FilmStorage filmStorage) {
        this.likesStorage = likesStorage;
        this.filmStorage = filmStorage;
    }

    @GetMapping("/films")
    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) {
        return filmStorage.create(film);
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film film) {
        return filmStorage.update(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        likesStorage.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        likesStorage.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public Collection<Film> getTopNPopularFilms(@RequestParam(required = false) Integer count) {
        return filmStorage.getTopFilms(count);
    }

    @GetMapping("/films/{id}")
    public Film getUserId(@PathVariable int id) {
        return filmStorage.getFilmId(id);
    }

}
