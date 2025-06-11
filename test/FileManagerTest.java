import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    static Path testDir;
    static Path testFile;
    static Path subDir;

    @BeforeEach
    void setup() throws IOException {
        testDir = Files.createTempDirectory("testDir");
        testFile = Files.createFile(testDir.resolve("file.txt"));
        try (FileWriter writer = new FileWriter(testFile.toFile())) {
            writer.write("Test content");
        }
        subDir = Files.createDirectory(testDir.resolve("subdir"));
        Files.createFile(subDir.resolve("file2.txt"));
    }

    @Test
    void testCountFiles() {
        int fileCount = FileManager.countFiles(testDir.toString());
        assertEquals(2, fileCount);
    }

    @Test
    void testCountDirs() {
        int dirCount = FileManager.countDirs(testDir.toString());
        assertEquals(1, dirCount);
    }

    @Test
    void testCopyAndMove() throws IOException {
        Path copyDest = Files.createTempDirectory("copyDest");
        FileManager.copyFile(testDir.toString(), copyDest.toString());
        assertTrue(Files.exists(copyDest.resolve(testDir.getFileName()).resolve("file.txt")));

        Path moveDest = Files.createTempDirectory("moveDest");
        FileManager.move(testFile.toString(), moveDest.toString());
        assertTrue(Files.exists(moveDest.resolve("file.txt")));
        assertFalse(Files.exists(testFile));
    }
}
