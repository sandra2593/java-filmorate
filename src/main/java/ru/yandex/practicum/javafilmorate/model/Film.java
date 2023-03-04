package ru.yandex.practicum.javafilmorate.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Film {
    int id;
    @NotBlank
    String name;
    @NotNull
    @Length(max = 200)
    String description;
    @NotNull
    LocalDate releaseDate;
    @Positive
    int duration;
}
