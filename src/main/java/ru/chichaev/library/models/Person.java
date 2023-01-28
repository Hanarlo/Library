package ru.chichaev.library.models;


import jakarta.persistence.*;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "user")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    @Column(name = "Year_of_birth")
    @NotNull(message = "Year of birth should not be empty")
    @DecimalMin(value = "1950", message = "Enter real age")
    private int yearOfBirth;

    public Person() {

    }

    public Person(String name, int yearOfBirth) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public Person(int id) {
        this.id = id;
    }

    public Person(int id, String name, int yearOfBirth) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        if (this.books == null){
            this.books = books;
        } else{
            this.books.addAll(books);
        }

    }
}
