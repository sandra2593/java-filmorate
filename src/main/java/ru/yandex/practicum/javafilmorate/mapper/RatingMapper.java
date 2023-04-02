package ru.yandex.practicum.javafilmorate.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.javafilmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingMapper implements RowMapper<Rating> {
    @Override
    public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Rating.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
