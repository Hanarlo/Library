package ru.chichaev.library.models;

import javax.validation.constraints.*;


public class Book {
    private int user_id;
    private int id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String author;

    @DecimalMin(value = "1900", message = "Enter the real year")
    @NotNull
    private int year;

    public Book() {
    }

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book(int id, String name, String author, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }


    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
