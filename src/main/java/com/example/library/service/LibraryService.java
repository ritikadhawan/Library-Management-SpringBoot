package com.example.library.service;

import com.example.library.entity.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {
    private final List<Book> bookList;

    public LibraryService() {
        bookList = new ArrayList<Book>();
    }

    public List<Book> getAllBooks() {
        return bookList;
    }
}
