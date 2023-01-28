package ru.chichaev.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chichaev.library.models.Book;
import ru.chichaev.library.models.Person;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    public List<Book> findBooksByOwner(Person user);

    public List<Book> findBookByNameStartsWith(String query);
}
