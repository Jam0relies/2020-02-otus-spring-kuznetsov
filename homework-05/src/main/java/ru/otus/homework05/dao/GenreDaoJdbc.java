package ru.otus.homework05.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework05.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;
    private final GenreMapper mapper = new GenreMapper();


    @Override
    public long count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from genre", Long.class);
    }

    @Override
    public void insert(Genre genre) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", genre.getId());
        params.put("name", genre.getName());
        jdbc.update("insert into genre (id, name) values (:id, :name)", params);
    }

    @Override
    public Genre getById(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.queryForObject("select * from genre where id = :id", params, this.mapper);
    }

    @Override
    public List<Genre> getByName(String name) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        return jdbc.query("select * from genre where name = :name", params, this.mapper);
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from genre", this.mapper);
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }
}
