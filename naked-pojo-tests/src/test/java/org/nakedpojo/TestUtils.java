package org.nakedpojo;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;

class TestUtils {
    static String fileText(String filename) throws Exception {
        InputStream file;
        file = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
        if(file == null)
            file = TestUtils.class.getResourceAsStream(filename);
        if(file == null)
            System.out.println("Error loading " + filename);

        return IOUtils.toString(file);
    }
}
