# Naked POJO

A Java-to-JavaScript DTO generator. Still heavily a work in progress.

Use case example:

```Java
package org.nakedpojo.model;

import org.nakedpojo.annotations.Naked;

@Naked
public class Book {
    @Naked
    public enum Genre {
        SF,
        BELETRISTICS;
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
```
This should render a JavaSript object with basic [knockout.js](http://knockoutjs.com/) bindings:

```JavaScript

    var Genre = [
        "SF",
        "BELETRISTICS"
    ];

    function Book() {
        self = this;

        self.title=ko.observable(undefined);
        self.genre=ko.observable({});
        self.author=ko.observable({});
        self.authors=ko.observableArray([]);

        self.update = function(dto) {
            self.title(dto.title);
            self.genre(dto.genre);
            self.author(dto.author);
            self.authors(dto.authors);
        }

        self.dto = function() {
            return ko.toJS(self);
        }
    }

```
