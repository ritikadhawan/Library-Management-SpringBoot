package com.example.library.service;

import com.example.library.database.BookRepository;
import com.example.library.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

class LibraryServiceTest {

    @InjectMocks
    LibraryService libraryService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllBooks() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("learn spring boot", "john doe", "programming book", "2022-04-01"));
        bookList.add(new Book("learn java", "jane doe", "programming book", "2022-04-01"));

        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> allBooks = libraryService.getAllBooks();

        assertThat(allBooks, is(equalTo(bookList)));
    }

    @Test
    void shouldReturnBookById() {
        Book book = new Book("learn spring boot", "john doe", "programming book", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        assertThat(libraryService.getById(book.getId()).get(), is(equalTo(book)));
    }

    @Test
    void shouldAddBook() {
        Book book = new Book("learn spring boot", "john doe", "programming book", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        Book bookAdded = libraryService.addBook(book);

        assertThat(bookAdded, is(equalTo(book)));
    }

    @Test
    void shouldUpdateBookByIdIfAlreadyPresent() {
        Book book = new Book("learn spring boot", "john doe", "programming book", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        Book updatedBook = new Book("hello java", "jane doe", "programming book", "2022-04-01");
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        libraryService.updateBook(updatedBook, book.getId());

        assertThat(libraryService.getById(book.getId()).get(), is(equalTo(updatedBook)));
    }

    @Test
    void shouldDeleteBookByIdIfAlreadyPresent() {
        Book book = new Book("learn spring boot", "john doe", "programming book", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(bookRepository.findById(book.getId())).thenReturn(null);

        libraryService.deleteById(book.getId());

        assertThat(libraryService.getById(book.getId()), is(nullValue()));
    }
}