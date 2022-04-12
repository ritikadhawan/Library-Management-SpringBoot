package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

public class Book {
    private int id;
    private String name;
    private String author;
    private String genre;

    @JsonCreator
    public Book(int id, String name, String author, String genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Book(String name, String author, String genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        return id == anotherBook.id && name.equals(anotherBook.name) && author.equals(anotherBook.author) && genre.equals(anotherBook.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, genre);
    }

}
