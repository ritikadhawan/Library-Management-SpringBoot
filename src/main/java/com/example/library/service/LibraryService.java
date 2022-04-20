package com.example.library.service;

import com.example.library.entity.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {
    private final List<Book> bookList;

    public LibraryService() {
        bookList = new ArrayList<>();
    }

    public LibraryService(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Book> getAllBooks() {
        return bookList;
    }

    public Book getById(String id) {
        Optional<Book> book = bookList.stream().filter(b -> b.getId().equals(id)).findFirst();
        return book.orElse(null);
    }

    public Book addBook(Book book) {
        bookList.add(book);
        return getById(book.getId());
    }

    public Boolean updateBook(Book book, String id) {
        if (getById(id) != null) {
            bookList.stream().filter(b -> b.getId().equals(id)).forEach(b -> {
                b.setName(book.getName());
                b.setAuthor(book.getAuthor());
                b.setGenre(book.getGenre());
                b.setPublishedOn(book.getPublishedOn());
            });
            return true;
        }
        return false;
    }

    public Boolean deleteById(String id) {
        if (getById(id) != null) {
            bookList.removeIf(b -> b.getId().equals(id));
            return true;
        } else {
            return false;
        }
    }
}
