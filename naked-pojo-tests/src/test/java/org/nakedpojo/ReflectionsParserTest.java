package org.nakedpojo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.nakedpojo.parser.ReflectionsParser;
import org.nakedpojo.model.Author;
import org.nakedpojo.model.Book;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;

import java.util.Set;

@RunWith(JUnit4.class)
public class ReflectionsParserTest {

    @Test
    public void testPerPackage() {
        Reflections reflections = new Reflections(
                ClasspathHelper.forPackage("org.nakedpojo.model"),
                new SubTypesScanner(false),
                ClasspathHelper.forClass(this.getClass()));
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);

        NakedPojo np = new NakedPojo(new ReflectionsParser(), classes.toArray());

        System.out.println(np.renderAll());
    }

    @Test
    public void testPerClass() {
        NakedPojo np = new NakedPojo(new ReflectionsParser(), Book.class, Author.class);
        System.out.println(np.renderAll());
    }

    @Test
    public void testNone() {
        System.out.println();
    }
}