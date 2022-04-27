package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.exception.NoRecordFoundException;
import com.example.library.service.LibraryService;
import com.sun.net.httpserver.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<List<Book>>(libraryService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getById(@RequestParam String id) {

        Optional<Book> book = libraryService.getById(id);

        if (book.isEmpty()) {
            throw new NoRecordFoundException("book not found");
        }
        return new ResponseEntity<Book>(book.get(), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addBook(@Valid @RequestBody Book book) {
        Book bookAdded = libraryService.addBook(book);
        return new ResponseEntity<String>(bookAdded.getId(), HttpStatus.CREATED);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBook(@Valid @RequestBody Book book, @RequestParam String id) {
        if (libraryService.getById(id).isEmpty()) {
            throw new NoRecordFoundException("book not found");
        }
        libraryService.updateBook(book, id);
        return new ResponseEntity<Authenticator.Success>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBook(@RequestParam String id) {
        if (libraryService.getById(id).isEmpty()) {
            throw new NoRecordFoundException("book not found");
        }
        libraryService.deleteById(id);
        return new ResponseEntity<Authenticator.Success>(HttpStatus.NO_CONTENT);
    }

}
