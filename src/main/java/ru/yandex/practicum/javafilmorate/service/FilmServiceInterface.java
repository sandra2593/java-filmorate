package ru.yandex.practicum.javafilmorate.service;

import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.List;

public interface FilmServiceInterface {
    void addNewLike(int id, int userId);
    void deleteLike(int id, int userId);
    List<Film> getTopFilms(Integer cnt);
}
