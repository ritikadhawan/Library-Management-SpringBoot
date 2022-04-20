package com.example.library.service;

import com.example.library.entity.Book;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class LibraryServiceTest {
    @Test
    void shouldReturnAllBooks() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("learn spring boot", "john doe", "programming book", new Date()));
        bookList.add(new Book("learn java", "jane doe", "programming book", new Date()));

        LibraryService libraryService = new LibraryService(bookList);

        List<Book> allBooks = libraryService.getAllBooks();

        assertThat(allBooks, is(equalTo(bookList)));
    }

    @Test
    void shouldReturnBookById() {
        Book book = new Book("learn spring boot", "john doe", "programming book", new Date());
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        LibraryService libraryService = new LibraryService(bookList);

        assertThat(libraryService.getById(book.getId()), is(equalTo(book)));
    }

    @Test
    void shouldAddBook() {
        Book book = new Book("learn spring boot", "john doe", "programming book", new Date());
        LibraryService libraryService = new LibraryService();

        Book bookAdded = libraryService.addBook(book);

        assertThat(bookAdded, is(equalTo(book)));
    }

    @Test
    void shouldUpdateBookByIdIfAlreadyPresent() {
        Book book = new Book("learn spring boot", "john doe", "programming book", new Date());
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        LibraryService libraryService = new LibraryService(bookList);

        Book updatedBook = new Book("hello java", "jane doe", "programming book", new Date());
        libraryService.updateBook(updatedBook, book.getId());

        assertThat(libraryService.getById(book.getId()), is(equalTo(updatedBook)));
    }

    @Test
    void shouldDeleteBookByIdIfAlreadyPresent() {
        Book book = new Book("learn spring boot", "john doe", "programming book", new Date());
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        LibraryService libraryService = new LibraryService(bookList);

        libraryService.deleteById(book.getId());

        assertThat(libraryService.getById(book.getId()), is(nullValue()));

    }
}