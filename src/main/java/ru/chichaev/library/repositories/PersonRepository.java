package ru.chichaev.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chichaev.library.models.Book;
import ru.chichaev.library.models.Person;

public interface PersonRepository  extends JpaRepository<Person, Integer> {
}
