package ru.yandex.practicum.javafilmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    @JsonIgnore
    FriendStatus friendStatus = FriendStatus.NONE;
    @JsonIgnore
    private final Set<Integer> friendSet = new HashSet<>();
}
