package ru.yandex.practicum.javafilmorate.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class Film {
    private int id;
//    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @Length(max = 200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive
    private int duration;
}
