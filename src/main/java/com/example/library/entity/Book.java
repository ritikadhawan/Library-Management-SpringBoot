package com.example.library.entity;

import com.example.library.validator.NotEmptyOrNull;
import com.example.library.validator.NotFutureDate;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Book {
    private final String id;

    @NotEmptyOrNull(message = "published on is required")
    @NotFutureDate(message = "published on date should not be in the future")
    private Date publishedOn;

    @NotEmptyOrNull(message = "name is required")
    private String name;

    @NotEmptyOrNull(message = "author is required")
    private String author;

    @NotEmptyOrNull(message = "genre is required")
    private String genre;


    @JsonCreator
    public Book(String name, String author, String genre, Date publishedOn) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.publishedOn = publishedOn;
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

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
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

        return name.equals(anotherBook.name) && author.equals(anotherBook.author) && genre.equals(anotherBook.genre) && publishedOn.equals(anotherBook.publishedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, genre, publishedOn);
    }

}
