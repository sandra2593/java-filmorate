package ru.yandex.practicum.javafilmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.storage.LikesStorage;

@Service
public class LikesService {
    private final LikesStorage likesStorage;

    @Autowired
    public LikesService(@Qualifier("DbLikesStorage") LikesStorage likesStorage) {
        this.likesStorage = likesStorage;
    }

    public void addLike(int id, int userId) {
        likesStorage.addLike(id, userId);
    }

    public void deleteLike(int id, int userId) {
        likesStorage.deleteLike(id, userId);
    }
}
