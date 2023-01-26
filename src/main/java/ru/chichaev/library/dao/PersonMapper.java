package ru.chichaev.library.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.chichaev.library.models.Person;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person();

            person.setId(rs.getInt("id"));
            person.setName(rs.getString("Name"));
            person.setYearOfBirth(rs.getInt("Year_of_birth"));

            return person;

    }
}
