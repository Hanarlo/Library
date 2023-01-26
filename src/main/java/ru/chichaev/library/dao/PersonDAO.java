package ru.chichaev.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.chichaev.library.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM user", new PersonMapper());
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM user WHERE id=?", new Object[]{id}, new PersonMapper()).stream()
                .findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO user (Name, Year_of_birth) VALUES(?,?)", person.getName(), person.getYearOfBirth());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE user SET name=?, Year_of_birth=? WHERE id=?", updatedPerson.getName()
                , updatedPerson.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM user WHERE id=?", id);

    }

}
