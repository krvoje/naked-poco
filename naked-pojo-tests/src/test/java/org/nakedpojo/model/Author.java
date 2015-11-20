package org.nakedpojo.model;

import org.nakedpojo.annotations.Naked;

import java.util.List;

@Naked
public class Author implements HumanInterface {
    public final String name;
    public final String surname;
    public final Book book;
    public final List<Book> books;
    public final boolean prolific;

    public Author(String name, String surname, Book book, List<Book> books, boolean prolific) {
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
}
