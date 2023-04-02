package ru.yandex.practicum.javafilmorate.model;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

@Data
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Rating {
    @Id
    int id;
    String name;
}
