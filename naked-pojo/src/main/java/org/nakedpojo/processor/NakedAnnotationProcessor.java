package org.nakedpojo.processor;

import org.nakedpojo.NakedPojo;
import org.nakedpojo.annotations.Naked;
import org.nakedpojo.configuration.Config;
import org.nakedpojo.parser.TypeMirrorParser;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
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

    private Config properties;

    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elements = processingEnv.getElementUtils();
        this.types = processingEnv.getTypeUtils();
        this.messager = processingEnv.getMessager();
        this.filer = processingEnv.getFiler();
        this.properties = new Config();

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

        // Let's not claim any annotations
        return false;
    }

    private void processNaked(Set<? extends Element> elements) {
        for(Element element: elements) {
            nakedPojo.scan(element);
        }
        if(properties.generationStrategy.equals(Config.GenerationStrategy.MULTIPLE_FILES)) {
            for(Element element: elements) {
                Naked naked = element.getAnnotation(Naked.class);
                if(naked.templateFilename().isEmpty()) {
                    nakedPojo.setTemplateType(naked.templateType());
                } else {
                    nakedPojo.setTemplateFilename(naked.templateFilename());
                }
                String targetTypeName = naked.targetTypeName().isEmpty() ?
                        element.getSimpleName().toString() :
                        naked.targetTypeName();
                String content = nakedPojo.render(element);
                writeToFile(targetTypeName + ".js", content);
            }
        }

        if(properties.generationStrategy.equals(Config.GenerationStrategy.SINGLE_FILE)) {
            String content = nakedPojo.renderAll();
            System.out.println(content);
            writeToFile(properties.targetFilename, content);
        }
    }

    private void writeToFile(String filename, String content) {
        try {
            System.out.println("Writing to file: " + filename);
            FileObject fl = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", filename);
            Writer writer = fl.openWriter();
            writer.write(content);
            writer.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
