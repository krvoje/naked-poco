package org.nakedpojo;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

class TestUtils {

    static String fileText(String filename) throws Exception {
        InputStream file = fileInputStream(filename);
        if(file == null)
            System.out.println("Error loading " + filename);

        return IOUtils.toString(file);
    }

    static InputStream fileInputStream(String filename) throws FileNotFoundException {
        InputStream file;
        file = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
        if(file == null)
            file = TestUtils.class.getResourceAsStream(filename);
        if(file == null)
            file = new FileInputStream(filename);

        return file;
    }
}
