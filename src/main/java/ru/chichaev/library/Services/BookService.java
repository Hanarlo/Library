package ru.chichaev.library.Services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chichaev.library.models.Book;
import ru.chichaev.library.models.Person;
import ru.chichaev.library.repositories.BookRepository;
import ru.chichaev.library.repositories.PersonRepository;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }


    public List<Book> index() {
        return bookRepository.findAll();
    }

    public List<Book> index(int page, int itemsPerPage) {
        return bookRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public List<Book> index(int page, int booksPerPage, Boolean sort) {
        if(booksPerPage > 0 && (sort != null && sort.equals(true))) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        } else if (booksPerPage > 0){
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        } else if (sort != null && sort.equals(true)){
            return bookRepository.findAll(Sort.by("year"));
        }
        return bookRepository.findAll();
    }

    public List<Book> takenBooks(int id) {
        Person user = personRepository.findById(id).orElse(null);
        List<Book> books = bookRepository.findBooksByOwner(user);
        for (Book b : books){
            b.setExpired(isExpired(b));
        }
        return books;
    }
    @Transactional
    public void save(Book book) {

        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);

    }

    public Book show(int id){
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void link(Person person, int id){
        Book updatedBook = bookRepository.findById(id).orElse(null);
        updatedBook.setDate(new Date());
        updatedBook.setOwner(person);
        person.setBooks(Collections.singletonList(updatedBook));
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void unlink(int id){
        Book updatedBook = bookRepository.findById(id).orElse(null);
        Person person = updatedBook.getOwner();
        updatedBook.setDate(null);
        updatedBook.setOwner(null);
        person.getBooks().remove(updatedBook);
        bookRepository.save(updatedBook);
    }

    public Boolean isExpired(Book book){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date bookDate = book.getDate();
        Calendar bookCalendar = Calendar.getInstance();
        bookCalendar.setTime(bookDate);
        if (calendar.get(Calendar.DAY_OF_MONTH) - bookCalendar.get(Calendar.DAY_OF_MONTH) >= 10){
            return true;
        }
        return false;
    }

    public List<Book> search(String searchQuery) {
        List<Book> books = bookRepository.findBookByNameStartsWith(searchQuery);
        return books;
    }
}
