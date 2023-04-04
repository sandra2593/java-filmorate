package ru.yandex.practicum.javafilmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.javafilmorate.model.Rating;
import ru.yandex.practicum.javafilmorate.service.RatingService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping()
    public Collection<Rating> getRatings() {
        return ratingService.getRatings();
    }

    @GetMapping("/{mpaId}")
    public Rating getRatingById(@PathVariable int mpaId) {
        return ratingService.getRatingById(mpaId);
    }

}