package org.nakedpojo.processor;

import org.nakedpojo.NakedPojo;
import org.nakedpojo.annotations.Naked;
import org.nakedpojo.javascript.ReflectionsParser;
import org.nakedpojo.javascript.TypeMirrorParser;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes(Naked.NAME)
public class NakedAnnotationProcessor extends AbstractProcessor {

    private NakedPojo nakedPojo;
    private Messager messager;

    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.nakedPojo = new NakedPojo(new TypeMirrorParser(processingEnv.getTypeUtils(), processingEnv.getElementUtils()));
        this.messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(TypeElement annotation: annotations) {
            messager.printMessage(Diagnostic.Kind.NOTE, annotation.getQualifiedName());
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            if(annotation.getQualifiedName().equals(Naked.class.getCanonicalName())) {
                processNaked(elements);
            }
        }
        messager.printMessage(Diagnostic.Kind.NOTE, nakedPojo.renderAll());
        return true;
    }

    private void processNaked(Set<? extends Element> elements) {
        for(Element e: elements) {
            messager.printMessage(Diagnostic.Kind.NOTE, "Processing " + e.getSimpleName().toString());
            processNaked(e);
        }
    }

    private void processNaked(Element element) {
        Naked naked = element.getAnnotation(Naked.class);
        String targetTypeName = naked.targetTypeName().isEmpty() ?
              element.getSimpleName().toString() :
                naked.targetTypeName();
        String content = nakedPojo.render(element);
        messager.printMessage(Diagnostic.Kind.NOTE, content);
        System.out.println(content);
    }
}
