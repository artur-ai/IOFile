package com.maiboroda.fileManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FileManagerITest {
    private File tempDir = new File("src/test/resources/test_resources");
    FileManager fileManager = new FileManager();

    @BeforeEach
    void creatResources() throws IOException {
        File dir1 = new File(tempDir, "dir1");
        File dir1_1 = new File(dir1, "dir1_1");
        File dir1_2 = new File(dir1, "dir1_2");

        dir1_1.mkdirs();
        dir1_2.mkdirs();

        new File(dir1_1, "file1_1.txt").createNewFile();
        new File(dir1_2, "file1_2.txt").createNewFile();
        new File(dir1, "file1.txt").createNewFile();

        File dir2 = new File(tempDir, "dir2");
        dir2.mkdirs();
        new File(dir2, "file2.txt").createNewFile();

        File dir3 = new File(tempDir, "dir3");
        dir3.mkdirs();
        new File(tempDir, "file0.txt").createNewFile();
    }

    @Test
    void testDirectoryExist() {
        assertTrue(new File(tempDir, "dir1").exists());
        assertTrue(new File(tempDir, "dir1/dir1_1").exists());
        assertTrue((new File(tempDir, "dir1/dir1_2").exists()));
        assertTrue(new File(tempDir, "dir2").exists());
        assertTrue(new File(tempDir, "dir3").exists());
    }

    @Test
    void testFileExist() {
        assertTrue(new File(tempDir, "dir1/dir1_1/file1_1.txt").exists());
        assertTrue((new File(tempDir, "dir1/dir1_2/file1_2.txt").exists()));
        assertTrue((new File(tempDir, "dir1/file1.txt").exists()));
        assertTrue(new File(tempDir, "dir2/file2.txt").exists());
        assertTrue((new File(tempDir, "file0.txt").exists()));
    }

    @Test
    void testCountFiles() {
        int result = fileManager.countFiles(tempDir.getAbsolutePath());
        assertEquals(5, result);
    }

    @Test
    void testCountFiles_DeleteFile() {
        assertTrue((new File(tempDir, "file0.txt").exists()));
        assertTrue((new File(tempDir, "file0.txt").delete()));
        assertFalse((new File(tempDir, "file0.txt").exists()));

        int result = fileManager.countFiles(tempDir.getAbsolutePath());
        assertEquals(4, result);
    }

    @Test
    void testCountFiles_CreateNewFile() throws IOException {
        assertTrue(new File(tempDir, "file8.txt").createNewFile());

        int result = fileManager.countFiles(tempDir.getAbsolutePath());
        assertTrue(new File(tempDir, "file8.txt").exists());
        assertEquals(6, result);
    }

    @Test
    void testCountDirs() {
        int result = fileManager.countDirs((tempDir.getAbsolutePath()));
        assertEquals(5, result);
    }

    @Test
    void testCountDirs_DeleteDirs() {
        File dir1 = new File(tempDir, "dir1");
        assertTrue(new File(tempDir, "dir1").exists());

        deleteTestResources(dir1);
        assertFalse(dir1.exists());

        int result = fileManager.countDirs(tempDir.getAbsolutePath());
        assertEquals(2, result);
    }

    @Test
    void testCountDirs_CreateNewDir() {
        assertTrue(new File(tempDir, "dir8").mkdirs());
        assertTrue(new File(tempDir, "dir9").mkdirs());

        assertTrue(new File(tempDir, "dir8").exists());
        assertTrue(new File(tempDir, "dir9").exists());

        int result = fileManager.countDirs(tempDir.getAbsolutePath());

        assertEquals(7, result);
        assertTrue(new File(tempDir, "dir8").delete());

        int resultAfterDelete = fileManager.countDirs(tempDir.getAbsolutePath());

        assertEquals(6, resultAfterDelete);
    }

    @Test
    void testCopyFile() throws IOException {
        File copyDir = new File(tempDir, "copiedDirectory");
        copyDir.mkdirs();

        String srcPath = new File(tempDir, "dir1").getAbsolutePath();
        String destPath = copyDir.getAbsolutePath();

        fileManager.copyFile(srcPath, destPath);

        File copiedDir = new File(copyDir, "dir1");
        File copiedFile1 = new File(copiedDir, "file1.txt");
        File copiedFile1_1 = new File(copiedDir, "dir1_1/file1_1.txt");
        File copiedFile1_2 = new File(copiedDir, "dir1_2/file1_2.txt");

        assertTrue(copiedDir.exists());
        assertTrue(copiedFile1.exists());
        assertTrue(copiedFile1_1.exists());
        assertTrue(copiedFile1_2.exists());
    }

    @Test
    void testMoveFiles() throws IOException {
        assertTrue((new File(tempDir, "file0.txt").exists()));
        File sourceFile = new File(tempDir, "dir1/file1.txt");
        File targetDir = new File(tempDir, "dir3");

        assertTrue(new File(tempDir, "dir1/file1.txt").exists());
        assertTrue(targetDir.exists());

        fileManager.move(sourceFile.getAbsolutePath(), targetDir.getAbsolutePath());

        File movedFile = new File(targetDir, sourceFile.getName());
        assertTrue(movedFile.exists());
        assertFalse(sourceFile.exists());
    }

    @AfterEach
    void cleanUp() {
        deleteTestResources(tempDir);
    }

    private void deleteTestResources(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File deleteFile : files) {
                    deleteTestResources(deleteFile);
                }
            }
        }
        file.delete();
    }
}
