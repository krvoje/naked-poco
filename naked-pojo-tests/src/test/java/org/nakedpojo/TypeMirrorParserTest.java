package org.nakedpojo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.nakedpojo.javascript.ReflectionsParser;
import org.nakedpojo.javascript.TypeMirrorParser;
import org.nakedpojo.model.Author;
import org.nakedpojo.model.Book;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;

import java.util.Set;

@RunWith(JUnit4.class)
public class TypeMirrorParserTest {

    @Test
    public void testNone() {
        System.out.println();
        final NakedPojo nakedPojo = new NakedPojo(new TypeMirrorParser(null));
    }
}