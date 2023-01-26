package ru.chichaev.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.chichaev.library.models.Book;
import ru.chichaev.library.models.Person;

import java.util.List;
@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
    }

    public List<Book> takenBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE user_id=?", new Object[]{id}, new BookMapper());
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (name, author, year) VALUES(?,?,?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET name=?, author=?, year=? WHERE id=?", updatedBook.getName()
                , updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", new Object[]{id});

    }

    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?", new Object[]{id}, new BookMapper()).stream().findAny().orElse(null);
    }

    public void link(int user_id, int id){
        jdbcTemplate.update("UPDATE book SET user_id = ? WHERE id = ?", new Object[]{user_id, id});
    }

    public void unlink(int id){
        jdbcTemplate.update("UPDATE book SET user_id = 0 WHERE id = ?", new Object[]{id});
    }
}
