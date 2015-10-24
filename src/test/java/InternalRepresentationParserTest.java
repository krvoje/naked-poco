import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.nakedpoco.javascript.InternalRepresentationParser;
import org.nakedpoco.javascript.JSType;
import org.nakedpoco.model.Author;
import org.nakedpoco.model.Book;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STGroupFile;

import java.util.ArrayList;
import java.util.Map;

@RunWith(JUnit4.class)
public class InternalRepresentationParserTest {

    @Test
    public void testToString() {
        InternalRepresentationParser parser = new InternalRepresentationParser();

        JSType arrayList = parser.convert(ArrayList.class);
        JSType author = parser.convert(Author.class);
        JSType book = parser.convert(Book.class);

        STGroupFile st = new STGroupFile("src/main/resources/JavaScriptObject.stg");

        for(Map.Entry<Class, JSType> mapping: parser.prototypes.entrySet()) {
            ST template = st.getInstanceOf("JavaScriptObject");
            JSType jsObj = mapping.getValue();
            template.add("jsObj", jsObj);
            System.out.println(template.render());
        }

    }

}
