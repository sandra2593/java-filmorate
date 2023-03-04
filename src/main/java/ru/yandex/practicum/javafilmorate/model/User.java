package ru.yandex.practicum.javafilmorate.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class User {
    int id;
    @NotBlank
    @Email
    String email;
    @NotBlank
    String login;
    String name;
    @NotNull
    @PastOrPresent
    LocalDate birthday;
}
