package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.Genre;

import java.util.Collection;

public interface GenreStorage {
    Collection<Genre> getGenres();

    Genre getGenreById(int id);
}
