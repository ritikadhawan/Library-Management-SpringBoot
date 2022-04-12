package com.example.library.service;

import com.example.library.entity.Book;
import org.junit.jupiter.api.Test;

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
}