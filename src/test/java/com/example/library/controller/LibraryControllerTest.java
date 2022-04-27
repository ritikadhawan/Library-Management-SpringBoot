package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LibraryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private LibraryService libraryService;

    @Test
    void shouldReturnAllBooks() throws Exception {

        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("learn spring boot", "john doe", "programming book", "2022-04-01"));
        bookList.add(new Book("learn java", "jane doe", "programming book", "2022-04-01"));

        when(libraryService.getAllBooks()).thenReturn(bookList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void shouldReturnBookById() throws Exception {

        Book book = new Book("learn spring boot", "john doe", "programming book", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.getById(book.getId())).thenReturn(Optional.of(book));

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.get("/api/v1/?id=" + book.getId()).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builders).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(book)));

    }

    @Test
    void shouldAddBook() throws Exception {

        Book book = new Book("learn spring boot", "john doe", "programming book", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.addBook(book)).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.content().string(book.getId()));

    }

    @Test
    void shouldUpdateBookByIdIfAlreadyPresent() throws Exception {
        Book book = new Book("learn spring boot", "john doe", "programming book", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.getById(book.getId())).thenReturn(Optional.of(book));

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.put("/api/v1/?id=" + book.getId()).contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isAccepted());
        verify(libraryService, times(1)).updateBook(book, book.getId());
    }

    @Test
    void shouldDeleteBookByIdIfAlreadyPresent() throws Exception {

        Book book = new Book("learn spring boot", "john doe", "programming book", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.getById(book.getId())).thenReturn(Optional.of(book));

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.delete("/api/v1/?id="
        + book.getId()).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builders).andExpect(status().isNoContent());
        verify(libraryService, times(1)).deleteById(anyString());
    }

    @Test
    void shouldReturnStatusBadRequestWhenNameOfBookIsEmpty() throws Exception {
        Book book = new Book("", "john doe", "programming book", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.addBook(book)).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenAuthorOfBookIsEmpty() throws Exception {
        Book book = new Book("introduction to java", "", "programming book", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.addBook(book)).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenPublishedAtOfBookIsNull() throws Exception {

        Book book = new Book("introduction to java", "jane doe", "programming", null);
        book.setId(UUID.randomUUID().toString());

        when(libraryService.addBook(book)).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenPublishedOnOfBookIsTheFutureDate() throws Exception {

        Book book = new Book("introduction to java", "jane doe", "programming", "2024-04-01");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.addBook(book)).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenGenreIsEmpty() throws Exception {
        Book book = new Book("introduction to java", "jane", "", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.addBook(book)).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenNameIsMoreThan50Characters() throws Exception {
        String name = RandomStringUtils.randomAlphabetic(51);
        Book book = new Book(name, "jane", "programming", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.addBook(book)).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenNameIsLessThan3Characters() throws Exception {
        String name = RandomStringUtils.randomAlphabetic(2);
        Book book = new Book(name, "jane", "programming", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.addBook(book)).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenAuthorIsMoreThan100Characters() throws Exception {
        String author = RandomStringUtils.randomAlphabetic(101);
        Book book = new Book("introduction to java", author, "programming", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.addBook(book)).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenAuthorIsLessThan3Characters() throws Exception {
        String author = RandomStringUtils.randomAlphabetic(2);
        Book book = new Book("introduction to java", author, "programming", "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.addBook(book)).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenGenreIsMoreThan500Characters() throws Exception {
        String genre = RandomStringUtils.randomAlphabetic(501);
        Book book = new Book("introduction to java", "jane doe", genre, "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.addBook(book)).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenGenreIsLessThan3Characters() throws Exception {
        String genre = RandomStringUtils.randomAlphabetic(2);
        Book book = new Book("introduction to java", "jane doe", genre, "2022-04-01");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.addBook(book)).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenBookToBeUpdatedIsNotPresent() throws Exception {
        Book book = new Book("introduction to java", "jane doe", "programming", "2022-04-01");
        book.setId(UUID.randomUUID().toString());
        UUID id = UUID.randomUUID();

        when(libraryService.getById(id.toString())).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.put("/api/v1/?id=" + id).contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenBookToBeDeletedIsNotPresent() throws Exception {
        UUID id = UUID.randomUUID();

        when(libraryService.getById(id.toString())).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.delete("/api/v1/?id=" + id).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestForGetBookByIdWhenBookIsNotPresent() throws Exception {

        UUID id = UUID.randomUUID();
        when(libraryService.getById(id.toString())).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.get("/api/v1/?id=" + id).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatusBadRequestWhenPublishedOnOfBookIsOfInvalidFormat() throws Exception {

        Book book = new Book("introduction to java", "jane doe", "programming", "2024");
        book.setId(UUID.randomUUID().toString());

        when(libraryService.addBook(book)).thenReturn(book);

        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/api/v1").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builders).andExpect(status().isBadRequest());
    }
}
