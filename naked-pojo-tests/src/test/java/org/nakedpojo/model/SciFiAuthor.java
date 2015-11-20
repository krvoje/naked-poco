package org.nakedpojo.model;

import org.nakedpojo.annotations.Naked;

import java.util.ArrayList;
import java.util.List;

@Naked
public class SciFiAuthor extends Author implements SciFiInterface {

    public final List<String> conventionsVisited = new ArrayList<>();

    public SciFiAuthor(String name,
                       String surname,
                       Book book,
                       List<Book> books,
                       boolean prolific,
                       List<String> conventionsVisited) {
        super(name, surname, book, books, prolific);
        this.conventionsVisited.addAll(conventionsVisited);
    }

    @Override
    public String getSubGenre() {
        return "Steampunk";
    }

    @Override
    public String getIdNumber() {
        return "12312445";
    }
}
