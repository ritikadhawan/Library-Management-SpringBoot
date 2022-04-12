package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.service.LibraryService;
import com.sun.net.httpserver.Authenticator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<List<Book>>(libraryService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getById(@RequestParam int id) {
        return new ResponseEntity<Book>(libraryService.getById(id), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return new ResponseEntity<Book>(libraryService.addBook(book), HttpStatus.CREATED);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBook(@RequestBody Book book, @RequestParam int id) {
        if (libraryService.updateBook(book, id)) {
            return new ResponseEntity<Authenticator.Success>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<Authenticator.Failure>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBook(@RequestParam int id) {
        if (libraryService.deleteById(id)) {
            return new ResponseEntity<Authenticator.Success>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<Authenticator.Failure>(HttpStatus.NOT_FOUND);
        }
    }

}
