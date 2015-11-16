package org.nakedpojo;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.nakedpojo.model.Author;
import org.nakedpojo.model.Book;
import org.nakedpojo.parser.ReflectionsParser;

import java.io.InputStream;

//@Ignore
@RunWith(JUnit4.class)
public class ReflectionsParserTest {

    @Test
    public void knockoutPerClass() throws Exception {
        NakedPojo np = new NakedPojo(new ReflectionsParser(),
                NakedPojo.TemplateType.KNOCKOUT_JS,
                Book.class, Author.class, Book.Genre.class);

        String bookJS = np.render(Book.class);
        String authorJS = np.render(Author.class);
        String genreJS = np.render(Book.Genre.class);

        Assert.assertEquals(fileText("Book_knockout_expected.js"), bookJS);
        Assert.assertEquals(fileText("Author_knockout_expected.js"), authorJS);
        Assert.assertEquals(fileText("Genre_knockout_expected.js"), genreJS);
    }

    static String fileText(String filename) throws Exception {
        InputStream file;
        file = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
        if(file == null)
            file = ReflectionsParserTest.class.getResourceAsStream(filename);
        if(file == null)
            System.out.println("Error loading " + filename);
        return IOUtils.toString(file);
    }
}