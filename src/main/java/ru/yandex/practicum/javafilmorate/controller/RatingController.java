package ru.yandex.practicum.javafilmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.javafilmorate.model.Rating;
import ru.yandex.practicum.javafilmorate.storage.RatingStorage;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/mpa")
public class RatingController {

    private final RatingStorage ratingStorage;

    @Autowired
    public RatingController(RatingStorage ratingStorage) {
        this.ratingStorage = ratingStorage;
    }

    @GetMapping()
    public Collection<Rating> getRatings() {
        return ratingStorage.getRatings();
    }

    @GetMapping("/{mpaId}")
    public Rating getRatingById(@PathVariable int mpaId) {
        return ratingStorage.getRatingById(mpaId);
    }
}