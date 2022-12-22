package ru.yandex.practicum.javafilmorate.validator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserValidationTest {
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
    public void userAllInfoReturnZeroErrors() {
        User user = User.builder()
                .email("mail@mail.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1995,12,28))
                .build();

        // список нарушений
        Set<ConstraintViolation<User>> violations = valid.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void userWrongEmailReturnEmailError() {
        User user = User.builder()
                .email("mailmail.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1995,12,28))
                .build();

        // список нарушений
        Set<ConstraintViolation<User>> violations = valid.validate(user);
        assertFalse(violations.isEmpty());
        ConstraintViolation<User> violation = violations.stream().findFirst().get();
        // проверяем на каком поле ошибка
        assertEquals(violation.getPropertyPath().toString(),"email");
    }

    @Test
    public void userEmptyLoginReturnLoginError() {
        User user = User.builder()
                .email("mail@mail.ru")
                .name("name")
                .birthday(LocalDate.of(1995,12,28))
                .build();

        // список нарушений
        Set<ConstraintViolation<User>> violations = valid.validate(user);
        assertFalse(violations.isEmpty());
        ConstraintViolation<User> violation = violations.stream().findFirst().get();
        // проверяем на каком поле ошибка
        assertEquals(violation.getPropertyPath().toString(),"login");
    }

    @Test
    public void userIncorrectBirthdayReturnBirthdayError() {
        User user = User.builder()
                .email("mail@mail.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2035,12,28))
                .build();

        // список нарушений
        Set<ConstraintViolation<User>> violations = valid.validate(user);
        assertFalse(violations.isEmpty());
        ConstraintViolation<User> violation = violations.stream().findFirst().get();
        // проверяем на каком поле ошибка
        assertEquals(violation.getPropertyPath().toString(),"birthday");
    }

}
