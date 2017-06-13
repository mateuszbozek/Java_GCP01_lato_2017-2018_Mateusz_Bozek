package com.lenda.marc.controller;

import com.lenda.marc.entity.Book;
import com.lenda.marc.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BooksCtrl {

    private static final Logger logger = LoggerFactory.getLogger(BooksCtrl.class);

    @Autowired
    BookRepository bookRepository;

    @CrossOrigin(origins = "http://localhost:8080")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/add_book")
    @ResponseBody
    public void addBook(@RequestBody Book book) {
        logger.info("Added new book: "+book.getTitle());

        bookRepository.save(book);
    }


    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/all_books", method = RequestMethod.GET)
    public List<Book> allBooks() {
        return bookRepository.findAll()
                .stream()
                .collect(Collectors.toList());
    }


    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/delete_book", method = RequestMethod.DELETE)
    public void deleteBook(@RequestBody Book book) {
        logger.info("Delete book:"+book.getTitle());

        Book bookToDelete = bookRepository.findByTitle(book.getTitle());
        bookRepository.delete(bookToDelete);
    }
}
