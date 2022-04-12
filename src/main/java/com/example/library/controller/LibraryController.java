package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
