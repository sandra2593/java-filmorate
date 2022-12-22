package ru.yandex.practicum.javafilmorate.validator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.javafilmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FilmValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator valid;


    @BeforeAll
    public static void create() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        valid = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void filmAllInfoReturnZeroErrors() {
        Film film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1995,12,28))
                .duration(10)
                .build();

        // список нарушений
        Set<ConstraintViolation<Film>> violations = valid.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void filmEmptyNameReturnErrorName() {
        Film film = Film.builder()
//                .name("")
                .description("description")
                .releaseDate(LocalDate.of(1995,12,28))
                .duration(10)
                .build();

        // список нарушений
        Set<ConstraintViolation<Film>> violations = valid.validate(film);
        assertFalse(violations.isEmpty());
        ConstraintViolation<Film> violation = violations.stream().findFirst().get();
        // проверяем на каком поле ошибка
        assertEquals(violation.getPropertyPath().toString(),"name");
    }

    @Test
    public void filmLongDescriptionReturnErrorDescription() {
        Film film = Film.builder()
                .name("name")
                .description("descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription")
                .releaseDate(LocalDate.of(1995,12,28))
                .duration(10)
                .build();

        // список нарушений
        Set<ConstraintViolation<Film>> violations = valid.validate(film);
        assertFalse(violations.isEmpty());
        ConstraintViolation<Film> violation = violations.stream().findFirst().get();
        // проверяем на каком поле ошибка
        assertEquals(violation.getPropertyPath().toString(),"description");
    }

    @Test
    public void filmNegativeDurationReturnErrorDuration() {
        Film film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1895,12,28))
                .duration(-10)
                .build();

        // список нарушений
        Set<ConstraintViolation<Film>> violations = valid.validate(film);
        assertFalse(violations.isEmpty());
        ConstraintViolation<Film> violation = violations.stream().findFirst().get();
        // проверяем на каком поле ошибка
        assertEquals(violation.getPropertyPath().toString(),"duration");
    }

}
