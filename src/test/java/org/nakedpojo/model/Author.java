package org.nakedpojo.model;

import java.util.List;

public class Author {
    public final String name;
    public final String surname;
    public final Book book;
    public final List<Book> books;

    public Author(String name, String surname, Book book, List<Book> books) {
        this.name = name;
        this.surname = surname;
        this.book = book;
        this.books = books;
    }
}
