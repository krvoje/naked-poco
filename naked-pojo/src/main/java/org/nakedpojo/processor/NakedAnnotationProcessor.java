package org.nakedpojo.processor;

import org.nakedpojo.NakedPojo;
import org.nakedpojo.annotations.Naked;
import org.nakedpojo.configuration.Config;
import org.nakedpojo.configuration.Config.*;
import org.nakedpojo.parser.TypeMirrorParser;
import org.nakedpojo.utils.TypeMirrorUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.*;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import static org.nakedpojo.utils.Commons.join;
import static org.nakedpojo.utils.Commons.nullOrEmpty;

@SupportedAnnotationTypes(Naked.NAME)
public class NakedAnnotationProcessor extends AbstractProcessor {

    private Types typeUtils;
    private Elements elementUtils;
    private Messager messager;
    private Filer filer;
    private TypeMirrorUtils utils;

    private Config config;
    private NakedPojo nakedPojo;

    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elementUtils = processingEnv.getElementUtils();
        this.typeUtils = processingEnv.getTypeUtils();
        this.messager = processingEnv.getMessager();
        this.filer = processingEnv.getFiler();
        this.utils = new TypeMirrorUtils(typeUtils, elementUtils, messager);
        this.config = new Config();

        this.nakedPojo = new NakedPojo(new TypeMirrorParser(typeUtils, this.elementUtils, messager));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if(roundEnv.processingOver()) return false;
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

    private String templateFilename(Naked naked) {
        return ! nullOrEmpty(naked.templateFilename()) ?
                naked.templateFilename()
                : naked.templateType().templateFilename;
    }

    private String targetTypeName(Naked naked, Element element) {
        return naked.targetTypeName().isEmpty() ?
                utils.simpleName(element)
                : naked.targetTypeName();
    }

    private void processNaked(Set<? extends Element> elements) {
        nakedPojo.scan(elements);

        Map<String, String> rendered = new HashMap<String, String>();
        for(Element element: elements) {
            Naked naked = element.getAnnotation(Naked.class);
            String templateFilename = templateFilename(naked);
            String targetTypeName = targetTypeName(naked, element);

            nakedPojo.setTargetTypeName(element, targetTypeName);
            String content = nakedPojo.render(element, templateFilename);

            rendered.put(targetTypeName,
                    rendered.containsKey(targetTypeName) ?
                    rendered.get(targetTypeName) + content
                    : content
            );
        }

        if(config.generationStrategy.equals(GenerationStrategy.MULTIPLE_FILES)) {
            for(Map.Entry<String, String> e: rendered.entrySet()) {
                String targetTypeName = e.getKey();
                String content = e.getValue();
                writeToFile(targetTypeName + ".js", content);
            }
        }

        if(config.generationStrategy.equals(GenerationStrategy.SINGLE_FILE)) {
            String content = join(rendered, "\n");
            writeToFile(config.targetFilename, content);
        }
    }

    public void writeToFile(String filename, String content) {
        try {
            System.out.println("Writing content to file:");
            System.out.println(content);
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
