package org.nakedpojo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.nakedpojo.TestUtils.fileText;


@RunWith(JUnit4.class)
public class TypeMirrorParserTest {

    @Test
    public void knockoutBook() throws Exception {
        assertEquals(fileText("Book_knockout_expected.js"),
                fileText("target/generated-test-sources/test-annotations/Book.js"));
    }

    @Test
    public void knockoutAuthor() throws Exception {
        assertEquals(fileText("Author_knockout_expected.js"),
                fileText("target/generated-test-sources/test-annotations/Author.js"));
    }

    @Test
    public void knockoutGenre() throws Exception {
        assertEquals(fileText("Genre_knockout_expected.js"),
                fileText("target/generated-test-sources/test-annotations/Genre.js"));
    }

    @Test
    public void knockoutSciFiAuthor() throws Exception {
        assertEquals(fileText("SciFiAuthor_knockout_expected.js"),
                fileText("target/generated-test-sources/test-annotations/SciFiAuthor.js"));
    }
}