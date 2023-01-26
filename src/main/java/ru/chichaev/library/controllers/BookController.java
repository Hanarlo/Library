package ru.chichaev.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chichaev.library.dao.BookDAO;
import ru.chichaev.library.dao.PersonDAO;
import ru.chichaev.library.models.Book;
import ru.chichaev.library.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("people", personDAO.index());
        model.addAttribute("personDAO", personDAO);
        return "books/show";
    }


    @PostMapping("/{id}/link")
    public String link(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        System.out.println(person.getId());
        System.out.println(id);
        bookDAO.link(person.getId(), id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/unlink")
    public String unlink(@PathVariable("id") int id){
        bookDAO.unlink(id);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newBook (@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook (@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                       @PathVariable("id") int id){
        if (bindingResult.hasErrors()) return "books/edit";
        bookDAO.update(id,book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
