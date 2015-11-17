package org.nakedpojo;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.nakedpojo.TestUtils.fileText;


@RunWith(JUnit4.class)
public class TypeMirrorParserTest {

    @Test
    public void knockoutPerClass() throws Exception {

        // Just a dummy invocation to get the needed stuff to classpath

        // Classes should be in generated-sources at this point

        ClassLoader cl = TypeMirrorParserTest.class.getClassLoader();

        assertEquals(fileText("Book_knockout_expected.js"),
                fileText("generated-test-sources/test-annotations/Book.js"));
        assertEquals(fileText("Author_knockout_expected.js"),
                fileText("generated-test-sources/test-annotations/Author.js"));
        assertEquals(fileText("Genre_knockout_expected.js"),
                fileText("generated-test-sources/test-annotations/Genre.js"));
    }
}