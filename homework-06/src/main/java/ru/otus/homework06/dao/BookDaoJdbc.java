package ru.otus.homework06.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework06.domain.Author;
import ru.otus.homework06.domain.Book;
import ru.otus.homework06.domain.Genre;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;
    private final BookExtractor extractor = new BookExtractor();

    @Override
    public long count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from book", Long.class);
    }

    @Override
    public void insert(Book book) {
        final Map<String, Object> bookParams = new HashMap<>(2);
        bookParams.put("id", book.getId());
        bookParams.put("name", book.getName());
        jdbc.update("insert into book (id, name) values (:id, :name)", bookParams);
        for (Genre genre : book.getGenres()) {
            final Map<String, Object> genreParams = new HashMap<>(2);
            genreParams.put("book_id", book.getId());
            genreParams.put("genre_id", genre.getId());
            jdbc.update("insert into book_genre (book_id, genre_id) values (:book_id, :genre_id)", genreParams);
        }
        for (Author author : book.getAuthors()) {
            final Map<String, Object> authorParams = new HashMap<>(2);
            authorParams.put("book_id", book.getId());
            authorParams.put("author_id", author.getId());
            jdbc.update("insert into book_author (book_id, author_id) values (:book_id, :author_id)", authorParams);
        }
    }

    @Override
    public Book getById(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        Map<Long, Book> found = jdbc.query("select book.name as book_name, book.id as book_id, " +
                "author.id as author_id, author.name as author_name, " +
                "genre.id as genre_id, genre.name as genre_name" +
                " from book " +
                "left join book_author on book.id = book_author.book_id " +
                "left join author on book_author.author_id = author.id " +
                "left join book_genre on book.id = book_genre.book_id " +
                "left join genre on book_genre.genre_id = genre.id where book.id=:id", params, extractor);
        return found.getOrDefault(id, null);
    }

    @Override
    public List<Book> getByName(String name) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        Map<Long, Book> found = jdbc.query("select book.name as book_name, book.id as book_id, " +
                "author.id as author_id, author.name as author_name, " +
                "genre.id as genre_id, genre.name as genre_name" +
                " from book " +
                "left join book_author on book.id = book_author.book_id " +
                "left join author on book_author.author_id = author.id " +
                "left join book_genre on book.id = book_genre.book_id " +
                "left join genre on book_genre.genre_id = genre.id where book.name=:name", params, extractor);
        return new ArrayList<>(found.values());
    }

    @Override
    public List<Book> getAll() {
        Map<Long, Book> found = jdbc.query("select book.name as book_name, book.id as book_id, " +
                "author.id as author_id, author.name as author_name, " +
                "genre.id as genre_id, genre.name as genre_name" +
                " from book " +
                "left join book_author on book.id = book_author.book_id " +
                "left join author on book_author.author_id = author.id " +
                "left join book_genre on book.id = book_genre.book_id " +
                "left join genre on book_genre.genre_id = genre.id", new BookExtractor());
        return new ArrayList<>(found.values());
    }

    private static class BookExtractor implements ResultSetExtractor<Map<Long, Book>> {
        public Map<Long, Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Book> books = new HashMap<>();
            while (rs.next()) {
                final Long bookId = rs.getLong("book_id");
                final String name = rs.getString("book_name");
                Set<Genre> genres;
                Set<Author> authors;
                final Book oldBook = books.get(bookId);
                if (oldBook != null) {
                    genres = oldBook.getGenres();
                    authors = oldBook.getAuthors();
                } else {
                    genres = new HashSet<>();
                    authors = new HashSet<>();
                }
                Long genreId = rs.getObject("genre_id", Long.class);
                if (genreId != null) {
                    final String genreName = rs.getString("genre_name");
                    final Genre genre = new Genre(genreId, genreName);
                    genres.add(genre);
                }
                Long authorId = rs.getObject("author_id", Long.class);
                if (authorId != null) {
                    String authorName = rs.getString("author_name");
                    final Author author = new Author(authorId, authorName);
                    authors.add(author);
                }
                final Book newBook = new Book(bookId, name, authors, genres);
                books.put(bookId, newBook);
            }
            return books;
        }
    }

    @Override
    public boolean delete(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.update("delete from book where id=:id; delete from book_author where book_id=:id", params) > 0;
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Book(id, name, null, null);
        }
    }
}
