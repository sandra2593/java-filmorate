package ru.yandex.practicum.javafilmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.service.FilmService;
import ru.yandex.practicum.javafilmorate.service.LikesService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;
    private final LikesService likesService;

    @Autowired
    public FilmController(LikesService likesService, FilmService filmService) {
        this.likesService = likesService;
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> findAll() {
        return filmService.findAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        likesService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        likesService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getTopNPopularFilms(@RequestParam(required = false) Integer count) {
        return filmService.getTopFilms(count);
    }

    @GetMapping("/{id}")
    public Film getUserId(@PathVariable int id) {
        return filmService.getFilmId(id);
    }

}
