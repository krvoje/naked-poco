package org.nakedpojo;

import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.nakedpojo.model.Author;
import org.nakedpojo.model.Book;
import org.nakedpojo.model.SciFiAuthor;
import org.nakedpojo.processor.NakedAnnotationProcessor;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.junit.Assert.assertEquals;
import static org.nakedpojo.TestUtils.fileText;
import static org.nakedpojo.utils.Commons.last;


@RunWith(JUnit4.class)
public class TypeMirrorParserTest {

    private void compile(String fqcn) throws Exception {
        String sourceText = fileText("src/test/java/" + fqcn.replaceAll("\\.", "/") + ".java");
        String className = last(fqcn.split("\\."));
        assert_().about(javaSource())
                .that(JavaFileObjects.forSourceString(fqcn, sourceText))
                .processedWith(new NakedAnnotationProcessor())
                .compilesWithoutError()
                .and()
                .generatesFiles(ggit JavaFileObjects.forResource(className + ".js"))
            ;
    }

    @Test
    public void knockoutBook() throws Exception {
        compile(Book.class.getCanonicalName());
        assertEquals(fileText("Book_knockout_expected.js"),
                fileText("target/generated-test-sources/test-annotations/Book.js"));
    }

    @Test
    public void knockoutAuthor() throws Exception {
        compile(Author.class.getCanonicalName());
        assertEquals(fileText("Author_knockout_expected.js"),
                fileText("target/generated-test-sources/test-annotations/Author.js"));
    }

    @Test
    public void knockoutGenre() throws Exception {
        compile(Book.class.getCanonicalName());
        assertEquals(fileText("Genre_knockout_expected.js"),
                fileText("target/generated-test-sources/test-annotations/Genre.js"));
    }

    @Test
    public void knockoutSciFiAuthor() throws Exception {
        compile(SciFiAuthor.class.getCanonicalName());
        assertEquals(fileText("SciFiAuthor_knockout_expected.js"),
                fileText("target/generated-test-sources/test-annotations/SciFiAuthor.js"));
    }
}