package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.Rating;
import java.util.Collection;

public interface RatingStorage {
    Collection<Rating> getRatings();

    Rating getRatingById(int id);
}
