package com.example.library.service;

import com.example.library.entity.Book;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class LibraryServiceTest {
    @Test
    void shouldReturnAllBooks() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "learn spring boot", "john doe", "programming book"));
        bookList.add(new Book(2, "learn java", "jane doe", "programming book"));

        LibraryService libraryService = new LibraryService(bookList);

        List<Book> allBooks = libraryService.getAllBooks();

        assertThat(allBooks, is(equalTo(bookList)));
    }

    @Test
    void shouldReturnBookById() {
        Book book = new Book(1, "learn spring boot", "john doe", "programming book");
        List<Book> bookList = new ArrayList<Book>();
        bookList.add(book);

        LibraryService libraryService = new LibraryService(bookList);

        assertThat(libraryService.getById(1), is(equalTo(book)));
    }

    @Test
    void shouldAddBook() {
        Book book = new Book(1, "learn spring boot", "john doe", "programming book");
        LibraryService libraryService = new LibraryService();

        Book bookAdded = libraryService.addBook(book);

        assertThat(bookAdded, is(equalTo(book)));
    }
}