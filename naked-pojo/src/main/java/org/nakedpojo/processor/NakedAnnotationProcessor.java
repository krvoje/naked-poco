package org.nakedpojo.processor;

import org.nakedpojo.NakedPojo;
import org.nakedpojo.annotations.Naked;
import org.nakedpojo.javascript.TypeMirrorParser;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes(Naked.NAME)
public class NakedAnnotationProcessor extends AbstractProcessor {

    private static final String TARGET_PATH = ".";

    private NakedPojo nakedPojo;

    private Types types;
    private Elements elements;
    private Messager messager;

    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elements = processingEnv.getElementUtils();
        this.types = processingEnv.getTypeUtils();
        this.messager = processingEnv.getMessager();

        this.nakedPojo = new NakedPojo(new TypeMirrorParser(types, elements, messager));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(TypeElement annotation: annotations) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            if(annotation.getQualifiedName().toString().equals(
                    Naked.class.getCanonicalName())) {
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

        writeToFile(targetTypeName+".js", content);
    }

    private void writeToFile(String filename, String content) {
        try {
            Writer writer = new FileWriter(TARGET_PATH + "/" + filename);
            writer.write(content);
            writer.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
