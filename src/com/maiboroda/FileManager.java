package com.maiboroda;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManager {

    public static int countFiles(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            return 0;
        }

        int count = 0;
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    count++;
                } else if (f.isDirectory()) {
                    count += countFiles(f.getAbsolutePath());
                }
            }
        }
        return count;
    }

    public static int countDirs(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            return 0;
        }
        int count = 0;

        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    count++;
                    count += countDirs(f.getAbsolutePath());
                }
            }
        }
        return count;
    }

    public static void copyFile(String srcPath, String destPath) {
        Path sourcePath = Paths.get(srcPath);
        Path targetPath = Paths.get(destPath).resolve(sourcePath.getFileName());

        try {
            if (Files.isDirectory(sourcePath)) {
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
                        exception.printStackTrace();
                    }
                });
            } else {
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public static void move(String from, String to) {
        Path source = Paths.get(from);
        Path target = Paths.get(to).resolve(source.getFileName());

        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}


