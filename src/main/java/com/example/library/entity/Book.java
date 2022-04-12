package com.example.library.entity;

public class Book {
    private int id;
    private final String name;
    private final String author;
    private final String genre;

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
}
