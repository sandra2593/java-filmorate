package ru.yandex.practicum.javafilmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.storage.GenreStorage;

import java.util.Collection;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreStorage genreStorage;

    @Autowired
    public GenreController(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    @GetMapping
    public Collection<Genre> getGenres() {
        return genreStorage.getGenres();
    }

    @GetMapping("/{genreId}")
    public Genre getGenreById(@PathVariable int genreId) {
        return genreStorage.getGenreById(genreId);
    }

}
