package ru.yandex.practicum.javafilmorate.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

//import javax.validation.constraints.NotNull;

@Data
@Builder
public class Rating {
    @Id
    private int id;
    private String name;
}
