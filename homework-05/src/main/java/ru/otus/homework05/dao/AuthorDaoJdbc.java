package ru.otus.homework05.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework05.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;
    private final AuthorMapper mapper = new AuthorMapper();

    @Override
    public long count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from author", Long.class);
    }

    @Override
    public void insert(Author author) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", author.getId());
        params.put("name", author.getName());
        jdbc.update("insert into author (id, name) values (:id, :name)", params);
    }

    @Override
    public Author getById(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.queryForObject("select * from author where id = :id", params, this.mapper);
    }

    @Override
    public List<Author> getByName(String name) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        return jdbc.query("select * from author where name = :name", params, this.mapper);
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select * from author", this.mapper);
    }

    @Override
    public boolean delete(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.update("delete from author where id=:id; delete from book_author where author_id=:id", params) > 0;
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Author(id, name);
        }
    }
}
