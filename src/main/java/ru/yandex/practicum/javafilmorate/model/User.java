package ru.yandex.practicum.javafilmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
public class User {
    @Id
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
    @JsonIgnore
    private final Set<Integer> friendSet = new HashSet<>();

    public String getName() {
        if (name == "") {
            return login;
        }
        return name;
    }
}
