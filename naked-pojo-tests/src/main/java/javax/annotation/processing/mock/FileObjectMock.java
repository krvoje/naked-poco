package javax.annotation.processing.mock;

import javax.tools.FileObject;
import java.io.*;
import java.net.URI;

public class FileObjectMock implements FileObject {

    private final File file;

    public FileObjectMock(File file) {
        this.file = file;
    }


    @Override
    public URI toUri() {
        return this.file.toURI();
    }

    @Override
    public String getName() {
        return this.file.getName();
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return new FileOutputStream(this.file);
    }

    @Override
    public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
        return new FileReader(file);
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        throw new MockNotImplementedException();
    }

    @Override
    public Writer openWriter() throws IOException {
        return new FileWriter(file);
    }

    @Override
    public long getLastModified() {
        throw new MockNotImplementedException();
    }

    @Override
    public boolean delete() {
        throw new MockNotImplementedException();
    }
}
