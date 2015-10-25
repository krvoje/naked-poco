package org.nakedpojo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.nakedpojo.model.Author;
import org.nakedpojo.model.Book;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;

@RunWith(JUnit4.class)
public class InternalRepresentationParserTest {
    @Test
    public void testPerPackage() {
        Reflections reflections = new Reflections(
            ClasspathHelper.forPackage("org.nakedpojo.model"), new SubTypesScanner(false));

        NakedPojo np = new NakedPojo(reflections);

        System.out.println(np.renderAll());
    }

    @Test
    public void testPerClass() {
        NakedPojo np = new NakedPojo(Book.class, Author.class);
        System.out.println(np.renderAll());
    }
}
