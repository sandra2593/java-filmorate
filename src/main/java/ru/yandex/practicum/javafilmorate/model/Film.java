package ru.yandex.practicum.javafilmorate.model;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Film {
    @Id
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
    Rating mpa;
    List<Genre> genres;
    int rate;
}
