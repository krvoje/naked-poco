import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.nakedpoco.javascript.InternalRepresentationParser;
import org.nakedpoco.javascript.JSClass;
import org.nakedpoco.model.Author;
import org.nakedpoco.model.Book;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import java.util.ArrayList;
import java.util.Map;

@RunWith(JUnit4.class)
public class InternalRepresentationParserTest {

    @Test
    public void testToString() {
        InternalRepresentationParser parser = new InternalRepresentationParser();

        JSClass arrayList = parser.convert(ArrayList.class);
        JSClass author = parser.convert(Author.class);
        JSClass book = parser.convert(Book.class);

        STGroupDir st = new STGroupDir("src/main/resources");

        for(Map.Entry<Class, JSClass> mapping: parser.mappings.entrySet()) {
            ST template = st.getInstanceOf("JavaScriptObject");
            JSClass jsObj = mapping.getValue();
            template.add("jsObj", jsObj);
            System.out.println(template.render());
        }

    }

}
