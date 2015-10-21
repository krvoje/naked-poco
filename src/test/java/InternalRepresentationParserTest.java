import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.nakedpoco.InternalRepresentationParser;
import org.nakedpoco.javascript.JSValue;

@RunWith(JUnit4.class)
public class InternalRepresentationParserTest {

    @Test
    public void testToString() {
        InternalRepresentationParser parser = new InternalRepresentationParser();
        JSValue value = parser.convert(Author.class);
        System.out.println(value.toString());
    }

}
