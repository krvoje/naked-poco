package javax.annotation.processing.mock;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

public class MessagerMock implements Messager{
    @Override
    public void printMessage(Diagnostic.Kind kind, CharSequence msg) {
        System.out.println(String.format("%x: %s", kind.toString(), msg.toString()));
    }

    @Override
    public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e) {
        throw new MockNotImplementedException();
    }

    @Override
    public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a) {
        throw new MockNotImplementedException();
    }

    @Override
    public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a, AnnotationValue v) {
        throw new MockNotImplementedException();
    }
}
