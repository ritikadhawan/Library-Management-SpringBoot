package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
public class LibraryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private LibraryService libraryService;

    @Test
    void shouldReturnAllBooks() throws Exception {

        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("learn spring boot", "john doe", "programming book", new Date()));
        bookList.add(new Book("learn java", "jane doe", "programming book", new Date()));

        when(libraryService.getAllBooks()).thenReturn(bookList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void shouldReturnBookById() throws Exception {

        Book book = new Book("learn spring boot", "john doe", "programming book", new Date());

        when(libraryService.getById(book.getId())).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.get("/api/v1/?id=" + book.getId()).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builders).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(book)));

    }

    @Test
    void shouldAddBook() throws Exception {

        Book book = new Book("learn spring boot", "john doe", "programming book", new Date());

        when(libraryService.addBook(any(Book.class))).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(book)));

    }

    @Test
    void shouldUpdateBookByIdIfAlreadyPresent() throws Exception {
        Book book = new Book("learn spring boot", "john doe", "programming book", new Date());

        when(libraryService.updateBook(any(Book.class), anyString())).thenReturn(true);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.put("/api/v1/?id=" + book.getId()).contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isAccepted());
        verify(libraryService, times(1)).updateBook(any(Book.class), anyString());
    }

    @Test
    void shouldDeleteBookByIdIfAlreadyPresent() throws Exception {

        when(libraryService.deleteById(anyString())).thenReturn(true);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.delete("/api/v1/?id=abc123").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builders).andExpect(status().isNoContent());
        verify(libraryService, times(1)).deleteById(anyString());
    }

    @Test
    void shouldReturnStatusBadRequestWhenNameOfBookIsEmpty() throws Exception {
        Book book = new Book("", "john doe", "programming book", new Date());

        when(libraryService.addBook(any(Book.class))).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenAuthorOfBookIsEmpty() throws Exception {
        Book book = new Book("introduction to java", "", "programming book", new Date());

        when(libraryService.addBook(any(Book.class))).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenPublishedAtOfBookIsNull() throws Exception {

        Book book = new Book("introduction to java", "jane doe", "programming", null);

        when(libraryService.addBook(any(Book.class))).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenPublishedOnOfBookIsTheFutureDate() throws Exception {

        Book book = new Book("introduction to java", "jane doe", "", new Date(2024, 4, 21));

        when(libraryService.addBook(any(Book.class))).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenGenreIsEmpty() throws Exception {
        Book book = new Book("introduction to java", "jane", "", new Date());

        when(libraryService.addBook(any(Book.class))).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenNameIsMoreThan50Characters() throws Exception {
        String name = RandomStringUtils.randomAlphabetic(51);
        Book book = new Book(name, "jane", "programming", new Date());

        when(libraryService.addBook(any(Book.class))).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenNameIsLessThan3Characters() throws Exception {
        String name = RandomStringUtils.randomAlphabetic(2);
        Book book = new Book(name, "jane", "programming", new Date());

        when(libraryService.addBook(any(Book.class))).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenAuthorIsMoreThan100Characters() throws Exception {
        String author = RandomStringUtils.randomAlphabetic(101);
        Book book = new Book("introduction to java", author, "programming", new Date());

        when(libraryService.addBook(any(Book.class))).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenAuthorIsLessThan3Characters() throws Exception {
        String author = RandomStringUtils.randomAlphabetic(2);
        Book book = new Book("introduction to java", author, "programming", new Date());

        when(libraryService.addBook(any(Book.class))).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenGenreIsMoreThan500Characters() throws Exception {
        String genre = RandomStringUtils.randomAlphabetic(501);
        Book book = new Book("introduction to java", "jane doe", genre, new Date());

        when(libraryService.addBook(any(Book.class))).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }
}
