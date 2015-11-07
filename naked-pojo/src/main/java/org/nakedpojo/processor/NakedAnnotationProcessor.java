package org.nakedpojo.processor;

import org.nakedpojo.NakedPojo;
import org.nakedpojo.Configuration;
import org.nakedpojo.annotations.Naked;
import org.nakedpojo.javascript.TypeMirrorParser;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.*;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes(Naked.NAME)
public class NakedAnnotationProcessor extends AbstractProcessor {

    private NakedPojo nakedPojo;

    private Types types;
    private Elements elements;
    private Messager messager;
    private Filer filer;

    private Configuration properties;

    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elements = processingEnv.getElementUtils();
        this.types = processingEnv.getTypeUtils();
        this.messager = processingEnv.getMessager();
        this.filer = processingEnv.getFiler();
        this.properties = new Configuration();

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
        return true;
    }

    private void processNaked(Set<? extends Element> elements) {
        for(Element element: elements) {
            String content = nakedPojo.render(element);
            if(properties.generationStrategy.equals(Configuration.GenerationStrategy.MULTIPLE_FILES)) {
                Naked naked = element.getAnnotation(Naked.class);
                String targetTypeName = naked.targetTypeName().isEmpty() ?
                        element.getSimpleName().toString() :
                        naked.targetTypeName();

                writeToFile(targetTypeName + ".js", content);
            }
        }

        if(properties.generationStrategy.equals(Configuration.GenerationStrategy.SINGLE_FILE)) {
            writeToFile(properties.targetFilename, nakedPojo.renderAll());
        }
    }

    private void writeToFile(String filename, String content) {
        try {
            FileObject fl = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", filename);
            Writer writer = fl.openWriter();
            writer.write(content);
            writer.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
