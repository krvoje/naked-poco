package org.nakedpojo.model;

import org.nakedpojo.annotations.Naked;

import java.util.List;

@Naked(targetTypeName = "AuthorCustomName")
public class AuthorCustom implements HumanInterface {
    private final String name;
    private String surname;
    public final Book book;
    public final List<Book> books;
    public final boolean prolific;

    public AuthorCustom(String name, String surname, Book book, List<Book> books, boolean prolific) {
        this.name = name;
        this.surname = surname;
        this.book = book;
        this.books = books;
        this.prolific = prolific;
    }

    @Override
    public String getIdNumber() {
        return "123123";
    }

    public String getName() {
        return this.name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
