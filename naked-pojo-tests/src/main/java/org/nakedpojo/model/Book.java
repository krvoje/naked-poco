package org.nakedpojo.model;

import org.nakedpojo.annotations.Naked;

@Naked
public class Book {
    @Naked
    public enum Genre {
        SF,
        BELETRISTIKA;
    }

    public final String title;
    public final Genre genre;
    public final Author author;
    public final Author[] authors;

    public Book(String title, Genre genre, Author author, Author[] authors) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.authors = authors;
    }
}
