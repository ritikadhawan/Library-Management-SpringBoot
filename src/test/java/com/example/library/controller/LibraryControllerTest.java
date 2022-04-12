package com.example.library.controller;

import com.example.library.controller.LibraryController;
import com.example.library.entity.Book;
import com.example.library.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
public class LibraryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    @Test
    void shouldReturnAllBooks() throws Exception {

        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "learn spring boot", "john doe", "programming book"));
        bookList.add(new Book(2, "learn java", "jane doe", "programming book"));

        when(libraryService.getAllBooks()).thenReturn(bookList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));

    }
}
