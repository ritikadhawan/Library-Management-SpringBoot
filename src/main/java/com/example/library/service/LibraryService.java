package com.example.library.service;

import com.example.library.database.BookRepository;
import com.example.library.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private BookRepository bookRepository;

    @Autowired
    public LibraryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getById(String id) {
        return bookRepository.findById(id);
    }

    public Book addBook(Book book) {
        bookRepository.save(book);
        Optional<Book> bookFound = bookRepository.findById(book.getId());
        return bookFound.orElse(null);
    }

    public void updateBook(Book book, String id) {

        Optional<Book> currBook = bookRepository.findById(id);
        if (currBook.isEmpty()) {
            return;
        }

        currBook.map(b -> {
            b.setName(book.getName());
            b.setAuthor(book.getAuthor());
            b.setGenre(book.getGenre());
            b.setPublishedOn(book.getPublishedOn());
            return bookRepository.save(b);
        });
    }

    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }
}
