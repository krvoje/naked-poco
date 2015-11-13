package org.nakedpojo;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.nakedpojo.model.Author;
import org.nakedpojo.model.Book;
import org.nakedpojo.parser.ReflectionsParser;

@RunWith(JUnit4.class)
@Ignore
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

    private static String fileText(String filename) throws Exception {
        return IOUtils.toString(ReflectionsParser.class.getResourceAsStream("/"+filename));
    }
}