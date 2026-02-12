package com.example.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileHandler {

    public byte[] readFile(String filename) throws IOException {
        // VULNERABILITY: Path Traversal
        // The application uses user-supplied input 'filename' directly to construct a
        // file path.
        // An attacker could supply input like "../../etc/passwd" to read arbitrary
        // files.
        File file = new File("updates/" + filename);

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            return data;
        }
    }
}

// Auto-generated update: 1770883678