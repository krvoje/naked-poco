package org.nakedpojo.processor;

import org.nakedpojo.NakedPojo;
import org.nakedpojo.annotations.Naked;
import org.nakedpojo.javascript.InternalRepresentationParser;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes(Naked.NAME)
public class NakedAnnotationProcessor extends AbstractProcessor {

    private final NakedPojo nakedPojo = new NakedPojo();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("Processing...");
        for(TypeElement annotation: annotations) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            if(annotation.getClass().equals(Naked.class)) {
                processNaked(elements);
            }
        }
        return true;
    }

    private void processNaked(Set<? extends Element> elements) {
        for(Element e: elements) {
            processNaked(e);
        }
    }

    private void processNaked(Element element) {
        Naked naked = element.getAnnotation(Naked.class);
        String targetTypeName = naked.targetTypeName().isEmpty() ?
              element.getSimpleName().toString() :
                naked.targetTypeName();
        String content = nakedPojo.render(element.getClass());
        System.out.println(content);
    }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
