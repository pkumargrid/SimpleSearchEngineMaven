import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Stage5Test {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testReadFileWhenFileExists() {
        try {
            String fileName = "testing.txt";
            String content = """
                            Dwight Joseph djo@gmail.com
                            Rene Webb webb@gmail.com
                            Katie Jacobs
                            Erick Harrington harrington@gmail.com
                            Myrtle Medina
                            Erick Burgess
                            """;
            Path path = Paths.get(fileName);
            Files.write(path, content.getBytes());
            String fileContent = Stage5.readFile(fileName);
            assertEquals(content, fileContent, "File content mismatch");
            assertTrue(Files.deleteIfExists(path), "Failed to delete file");
        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        }
    }

    @Test
    void testReadFileWhenFileDoesNotExist() {
        assertThrows(IOException.class, () -> Stage5.readFile("testing.txt"), "Working fine!");
    }

    @Test
    void testPrint() {
        Stage5.print();
        String expectedMenu = "=== Menu ===\n" +
                "1. Find a person\n" +
                "2. Print all people\n" +
                "0. Exit\n";
        assertEquals(expectedMenu, outputStream.toString());
    }

    @Test
    void testMainChoice0() {
        testMainChoice("0\n", "Bye!");
    }

    @Test
    void testMainChoice1() {
        testMainChoice("1\nRene\n0\n", "Rene Webb webb@gmail.com\n" + "Bye!");
    }

    @Test
    void testMainChoice2() {
        testMainChoice("2\n0\n", "=== List of people ===\n" +
                "Dwight Joseph djo@gmail.com\n" +
                "Rene Webb webb@gmail.com\n" +
                "Katie Jacobs\n" +
                "Erick Harrington harrington@gmail.com\n" +
                "Myrtle Medina\n" +
                "Erick Burgess\n" +
                "Bye!");
    }


    @Test
    void testMainIncorrectChoice(){
        testMainChoice("-1\n0\n", "Incorrect option! Try again.\n" + "Bye!");
    }


    @Test
    void testMainInputMismatch() {
        testMainChoice("wrongInput\n0\n", "Incorrect option! Try again.\n" + "Bye!");
    }

    @Test
    void testNoMatchingPersonFound(){
        testMainChoice("1\nPratik\n0\n","No matching people found.\n" + "Bye!");
    }

    private void testMainChoice(String choices, String expectedOutput) {
        try {
            String fileName = "testing.txt";
            String content = """
                            Dwight Joseph djo@gmail.com
                            Rene Webb webb@gmail.com
                            Katie Jacobs
                            Erick Harrington harrington@gmail.com
                            Myrtle Medina
                            Erick Burgess
                            """;
            Path path = Paths.get(fileName);
            Files.write(path, content.getBytes());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(choices.getBytes(StandardCharsets.UTF_8));
            System.setIn(inputStream);
            Stage5.main(new String[]{"",fileName});
            assertEquals(expectedOutput.trim(), outputStream.toString().trim());
            assertTrue(Files.deleteIfExists(path),"Failed to delete file");
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}