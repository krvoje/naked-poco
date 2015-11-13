package org.nakedpojo;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.nakedpojo.model.Author;
import org.nakedpojo.model.Book;
import org.nakedpojo.parser.ReflectionsParser;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TypeMirrorParserTest {

    @Test
    public void knockoutPerClass() throws Exception {

        // Classes should be in generated-sources at this point

        assertEquals(fileText("Book_knockout_expected.js"),
                fileText("Book.js"));
        assertEquals(fileText("Author_knockout_expected.js"),
                fileText("Author.js"));
        assertEquals(fileText("Genre_knockout_expected.js"),
                fileText("Genre.js"));
    }

    private static String fileText(String filename) throws Exception {
        return IOUtils.toString(ReflectionsParser.class.getResourceAsStream("/"+filename));
    }
}