package com.maiboroda.fileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.NoSuchElementException;

public class FileManager {
    public int countFiles(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            return 0;
        }

        int count = 0;
        File[] files = file.listFiles();
        if (files != null) {
            for (File ourFile : files) {
                if (ourFile.isFile()) {
                    count++;
                } else if (ourFile.isDirectory()) {
                    count += countFiles(ourFile.getAbsolutePath());
                }
            }
        }
        return count;
    }

    public int countDirs(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            return 0;
        }
        int count = 0;

        File[] files = file.listFiles();
        if (files != null) {
            for (File ourFile : files) {
                if (ourFile.isDirectory()) {
                    count++;
                    count += countDirs(ourFile.getAbsolutePath());
                }
            }
        }
        return count;
    }

    public void copyFile(String srcPath, String destPath) throws IOException {
        Path sourcePath = Paths.get(srcPath);
        Path targetPath = Paths.get(destPath).resolve(sourcePath.getFileName());

        if (Files.isDirectory(sourcePath)){
            Files.walk(sourcePath).forEach(path -> {
                try {
                    Path relativePath = sourcePath.relativize(path);
                    Path destination = targetPath.resolve(relativePath);

                    if (Files.isDirectory(path)) {
                        Files.createDirectories(destination);
                    } else {
                        Files.copy(path, destination, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException exception) {
                    throw new NoSuchElementException("Error with copy", exception);
                }
            });
        } else {
            Files.copy(sourcePath, targetPath.resolve(sourcePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }
    }


    public void move(String from, String to) throws IOException {
        Path source = Paths.get(from);
        Path target = Paths.get(to).resolve(source.getFileName());

        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new IOException("Error with move element");

        }
    }
}


