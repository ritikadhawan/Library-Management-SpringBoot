package com.example.library.entity;

import com.example.library.validator.NotEmptyOrNull;
import com.example.library.validator.NotFutureDate;
import com.example.library.validator.ValidDateFormat;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table
public class Book {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @NotEmptyOrNull(message = "published on is required")
    @NotFutureDate(message = "published on date should not be in the future")
    @ValidDateFormat(message = "valid date format required")
    private String publishedOn;

    @NotEmptyOrNull(message = "name is required")
    @Size(max = 50, min = 3, message = "name should be more than equal to {min} characters and less than equal to {max}")
    private String name;

    @NotEmptyOrNull(message = "author is required")
    @Size(max = 100, min = 3, message = "author should be more than equal to {min} characters and less than equal to {max}")
    private String author;

    @NotEmptyOrNull(message = "genre is required")
    @Size(max = 500, min = 3, message = "genre should be more than equal to {min} characters and less than equal to {max}")
    private String genre;

    public Book () {

    }
    @JsonCreator
    public Book(String name, String author, String genre, String publishedOn) {
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

    public String getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }

    public void setId(String id) {
        this.id = id;
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
