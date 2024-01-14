package com.rkgn.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {
    public static void transferFile(File src, File dest) throws IOException {
        try (BufferedOutputStream os = new BufferedOutputStream(Files.newOutputStream(dest.toPath()));
             BufferedInputStream is = new BufferedInputStream(Files.newInputStream(src.toPath()))) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        }
    }
}
