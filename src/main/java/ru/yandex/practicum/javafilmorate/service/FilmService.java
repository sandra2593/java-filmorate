package ru.yandex.practicum.javafilmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addNewLike(int id, int userId) {
        User user = userStorage.getUserId(userId);
        Film film = filmStorage.getFilmId(id);

        film.getLikeSet().add(user.getId());
    }

    public void deleteLike(int id, int userId) {
        User user = userStorage.getUserId(userId);
        Film film = filmStorage.getFilmId(id);

        film.getLikeSet().remove(user.getId());
    }

    public List<Film> getTopFilms(Integer cnt) {
        if (cnt == null) {
            cnt = 10;
        }
        List<Film> filmList =  filmStorage.findAll().stream().collect(Collectors.toList());
        return filmList.stream().sorted(Comparator.comparingInt(Film::getCountLike).reversed()).limit(cnt).collect(Collectors.toList());
    }

}
