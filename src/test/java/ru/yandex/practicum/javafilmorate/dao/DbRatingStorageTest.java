package ru.yandex.practicum.javafilmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.javafilmorate.exception.EnumOfExceptions;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Rating;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DbRatingStorageTest {
    private final DbRatingStorage dbRatingStorage;
    private Collection<Rating> d_rating = List.of(
            Rating.builder().id(1).name("G").build(),
            Rating.builder().id(2).name("PG").build(),
            Rating.builder().id(3).name("PG-13").build(),
            Rating.builder().id(4).name("R").build(),
            Rating.builder().id(5).name("NC-17").build()
    );

    @Test
    public void testRatingList() {
        Collection<Rating> dbRating = dbRatingStorage.getRatings();

        assertThat(dbRating)
                .hasSize(5)
                .containsAll(d_rating);
    }

    @Test
    public void testRatingIdNotExisting() {
        assertThatThrownBy(() -> {
            dbRatingStorage.getRatingById(-1);
        }).isInstanceOf(NotFoundException.class).hasMessage(EnumOfExceptions.NO_RATING.getExp());
    }
}
