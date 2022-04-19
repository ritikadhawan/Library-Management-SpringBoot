package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;
import java.util.UUID;

public class Book {
    private final String id;
    private String name;
    private String author;
    private String genre;

    @JsonCreator
    public Book(String name, String author, String genre) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Book.class) {
            return false;
        }
        Book anotherBook = (Book) obj;

        return name.equals(anotherBook.name) && author.equals(anotherBook.author) && genre.equals(anotherBook.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, genre);
    }

}
