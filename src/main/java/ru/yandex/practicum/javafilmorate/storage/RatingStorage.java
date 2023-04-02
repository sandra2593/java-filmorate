package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.Rating;

import java.util.Collection;
import java.util.List;

public interface RatingStorage {
    Collection<Rating> getRatings();
    Rating getRatingById(int id);
}
