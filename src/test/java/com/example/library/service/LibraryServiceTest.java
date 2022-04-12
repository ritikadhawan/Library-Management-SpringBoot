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
        LibraryService libraryService = new LibraryService();

        List<Book> bookList = libraryService.getAllBooks();

        assertThat(((List<?>) bookList).isEmpty(), is(equalTo(true)));
    }

    @Test
    void shouldReturnBookById() {
        Book book = new Book(1, "learn spring boot", "john doe", "programming book");
        List<Book> bookList = new ArrayList<Book>();
        bookList.add(book);

        LibraryService libraryService = new LibraryService(bookList);

        assertThat(libraryService.getById(1), is(equalTo(book)));
    }
}