package javax.annotation.processing.mock;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProcessingEnvironmentMock implements ProcessingEnvironment{

    private final Map<String, String> options = new HashMap<>();
    
    private final Messager messager;
    private final Filer filer;
    private final Elements elementUtils;
    private final Types typeUtils;
    private final SourceVersion sourceVersion;
    private final Locale locale;
    
    public ProcessingEnvironmentMock(
            final Messager messager,
            final Filer filer,
            final Elements elementUtils,
            final Types typeUtils,
            final SourceVersion sourceVersion,
            final Locale locale
    ){
        this.messager = messager;
        this.filer = filer;
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
        this.sourceVersion = sourceVersion;
        this.locale = locale;
    }

    @Override
    public Map<String, String> getOptions() {
        return this.options;
    }

    @Override
    public Messager getMessager() {
        return this.messager;
    }

    @Override
    public Filer getFiler() {
        return this.filer;
    }

    @Override
    public Elements getElementUtils() {
        return this.elementUtils;
    }

    @Override
    public Types getTypeUtils() {
        return this.typeUtils;
    }

    @Override
    public SourceVersion getSourceVersion() {
        return this.sourceVersion;
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }
}
