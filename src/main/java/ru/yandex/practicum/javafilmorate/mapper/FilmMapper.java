package ru.yandex.practicum.javafilmorate.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Rating r = Rating.builder()
                .id(rs.getInt("rating_id"))
                .name(rs.getString("rating_name"))
                .build();
        return Film.builder()
                .id(rs.getInt("id"))
                .mpa(r)
                .genres(mapGenres(rs.getString("genres_ids"), rs.getString("genres_names")))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(rs.getInt("duration"))
                .build();
    }

    private List<Genre> mapGenres(String genresIdsString, String genresNames) {
        if (genresIdsString != null) {
            String[] parsedGenresIds = genresIdsString.split("~");
            String[] parsedGenresNames = genresNames.split("~");
            List<Genre> genres = new ArrayList<>();
            for (int i = 0; i < parsedGenresIds.length; i++) {
                genres.add(Genre.builder()
                        .id(Integer.parseInt(parsedGenresIds[i]))
                        .name(parsedGenresNames[i])
                        .build());
            }
            return genres;
        }
        return List.of();
    }
}
