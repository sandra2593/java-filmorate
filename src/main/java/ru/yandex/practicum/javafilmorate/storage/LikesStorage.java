package ru.yandex.practicum.javafilmorate.storage;

public interface LikesStorage {
    void addLike(int id, int userId);
    void deleteLike(int id, int userId);
}
