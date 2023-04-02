package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    Film create(Film newFilm);

    Film update(Film newFilm);

    List<Film> findAll();

    Film getFilmId(int id);
    List<Film> getTopFilms(Integer count);
}
