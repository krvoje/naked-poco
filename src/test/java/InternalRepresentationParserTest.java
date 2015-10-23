import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.nakedpoco.javascript.InternalRepresentationParser;
import org.nakedpoco.javascript.JSObj;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import java.util.ArrayList;

@RunWith(JUnit4.class)
public class InternalRepresentationParserTest {

    @Test
    public void testToString() {
        InternalRepresentationParser parser = new InternalRepresentationParser();

        JSObj arrayList = parser.convert(ArrayList.class);
        System.out.println(arrayList.toString());

        JSObj author = parser.convert(Author.class);
        System.out.println(author.toString());

        JSObj book = parser.convert(Book.class);
        System.out.println(book.toString());

        STGroupDir st = new STGroupDir("src/main/resources");

        ST template = st.getInstanceOf("JavaScriptObject");
        template.add("jsObj", arrayList);
        System.out.println(template.render());

        template = st.getInstanceOf("JavaScriptObject");
        template.add("jsObj", author);
        System.out.println(template.render());

        template = st.getInstanceOf("JavaScriptObject");
        template.add("jsObj", book);
        System.out.println(template.render());
    }

}
