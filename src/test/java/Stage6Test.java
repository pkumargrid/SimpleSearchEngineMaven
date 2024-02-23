import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class Stage6Test {

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
            String fileContent = Stage6.readFile(fileName);
            assertEquals(content, fileContent, "File content mismatch");
            assertTrue(Files.deleteIfExists(path), "Failed to delete file");
        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        }
    }

    @Test
    void testReadFileWhenFileDoesNotExist() {
        assertThrows(IOException.class, () -> Stage6.readFile("testing.txt"), "Working fine!");
    }

    @Test
    void testPrint() {
        Stage6.print();
        String expectedMenu = "=== Menu ===\n" +
                "1. Find a person\n" +
                "2. Print all people\n" +
                "0. Exit\n";
        assertEquals(expectedMenu, outputStream.toString());
    }

    @Test
    void testCreateInvertedIndex() {
        ArrayList<String> listOfPerson = new ArrayList<>(Arrays.asList(
                "Katie Jacobs",
                "Erick Harrington harrington@gmail.com"
        ));
        Map<String, Set<Integer>> expectedResult = new HashMap<>();
        expectedResult.put("katie", new HashSet<>(Collections.singletonList(0)));
        expectedResult.put("jacobs", new HashSet<>(Collections.singletonList(0)));
        expectedResult.put("erick", new HashSet<>(Collections.singletonList(1)));
        expectedResult.put("harrington", new HashSet<>(Collections.singletonList(1)));
        expectedResult.put("harrington@gmail.com", new HashSet<>(Collections.singletonList(1)));
        Map<String, Set<Integer>> actualResult = Stage6.createInvertedIndex(listOfPerson);
        assertEquals(expectedResult, actualResult, "Should return the inverted index");
    }

    @Test
    void testMainChoice0() {
        testMainChoice("0\n", "Bye!");
    }

    @Test
    void testMainChoice1All() {
        testMainChoice("1\nALL\nRene\n0\n", "Rene Webb webb@gmail.com\n" + "Bye!");
    }
    @Test
    void testMainChoice1Any() {
        testMainChoice("1\nANY\nRene\n0\n", "Rene Webb webb@gmail.com\n" + "Bye!");
    }

    @Test
    void testMainChoice1None() {
        testMainChoice("1\nNONE\nrene katie erick medina\n0\n",
                "Dwight Joseph djo@gmail.com\n" + "Bye!");
    }

    @Test
    void testMainInvalidStrategy() {
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
            String choices = "1\n" + "FOR\n" + "hello\n";
            ByteArrayInputStream inputStream = new ByteArrayInputStream((choices).getBytes(StandardCharsets.UTF_8));
            System.setIn(inputStream);
            assertThrows(IllegalStateException.class,() -> Stage6.main(new String[]{"",fileName}),"Strategy found was right");
            assertTrue(Files.deleteIfExists(path),"Failed to delete file");
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
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
        testMainChoice("1\nALL\nPratik\n0\n","No matching person found.\n" + "Bye!");
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
            Stage6.main(new String[]{"",fileName});
            assertEquals(expectedOutput.trim(), outputStream.toString().trim());
            assertTrue(Files.deleteIfExists(path),"Failed to delete file");
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}