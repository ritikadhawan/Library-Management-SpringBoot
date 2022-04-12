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

    public Book getById(int id) {
        Optional<Book> book = bookList.stream().filter(b -> b.getId() == id).findFirst();
        return book.orElse(null);
    }

    public Book addBook(Book book) {
        bookList.add(book);
        return getById(book.getId());
    }

    public Boolean updateBook(Book book, int id) {
        if (book.getId() == id && getById(id) != null) {
            bookList.stream().filter(b -> b.getId() == id).forEach(b -> {
                b.setName(book.getName());
                b.setAuthor(book.getAuthor());
                b.setGenre(book.getGenre());
            });
            return true;
        }
        return false;
    }

    public Boolean deleteById(int id) {
        if (getById(id) != null) {
            bookList.removeIf(book -> book.getId() == id);
            return true;
        } else {
            return false;
        }
    }
}
