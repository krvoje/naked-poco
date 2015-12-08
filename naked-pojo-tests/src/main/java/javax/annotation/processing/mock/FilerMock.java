package javax.annotation.processing.mock;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;

public class FilerMock implements Filer {

    @Override
    public JavaFileObject createSourceFile(CharSequence name, Element... originatingElements) throws IOException {
        throw new MockNotImplementedException();
    }

    @Override
    public JavaFileObject createClassFile(CharSequence name, Element... originatingElements) throws IOException {
        throw new MockNotImplementedException();
    }

    @Override
    public FileObject createResource(JavaFileManager.Location location,
                                     CharSequence pkg,
                                     CharSequence relativeName, Element... originatingElements) throws IOException {
        return null;
    }

    @Override
    public FileObject getResource(JavaFileManager.Location location, CharSequence pkg, CharSequence relativeName) throws IOException {
        throw new MockNotImplementedException();
    }
}
