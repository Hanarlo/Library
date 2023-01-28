package ru.chichaev.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chichaev.library.Services.BookService;
import ru.chichaev.library.Services.PersonService;
import ru.chichaev.library.models.Book;
import ru.chichaev.library.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(value = "books_per_page", required = false, defaultValue = "0") Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) Boolean sort){
        model.addAttribute("books", bookService.index(page, booksPerPage, sort));
        return "books/index";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.show(id));
        model.addAttribute("people", personService.index());
        model.addAttribute("personDAO", personService);
        return "books/show";
    }


    @PostMapping("/{id}/link")
    public String link(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        bookService.link(person, id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/unlink")
    public String unlink(@PathVariable("id") int id){
        bookService.unlink(id);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newBook (@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "books/new";
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook (@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.show(id));
        return "books/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                       @PathVariable("id") int id){
        if (bindingResult.hasErrors()) return "books/edit";
        bookService.update(id,book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(){
        return "books/search";
    }

    @PostMapping("/search")
    public String search(@RequestParam(value = "search") String searchQuery, Model model){
        model.addAttribute("books", bookService.search(searchQuery));
        return "books/search";
    }
}
