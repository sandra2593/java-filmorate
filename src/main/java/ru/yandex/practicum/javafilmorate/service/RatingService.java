package ru.yandex.practicum.javafilmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.model.Rating;
import ru.yandex.practicum.javafilmorate.storage.RatingStorage;

import java.util.Collection;

@Service
public class RatingService {
    private final RatingStorage ratingStorage;

    @Autowired
    public RatingService(@Qualifier("DbRatingStorage") RatingStorage ratingStorage) {
        this.ratingStorage = ratingStorage;
    }

    public Collection<Rating> getRatings() {
        return ratingStorage.getRatings();
    }

    public Rating getRatingById(int id) {
        return ratingStorage.getRatingById(id);
    }
}
