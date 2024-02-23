import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Stage3Test {

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
    void print() {
        Stage3.print();
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
    void testMainChoice1() {
        testMainChoice("1\nRene\n0\n", "Rene Webb webb@gmail.com\n" + "Bye!");
    }

    @Test
    void testMainIncorrectChoice(){
        testMainChoice("-1\n0\n", "Incorrect option! Try again.\n" + "Bye!");
    }

    @Test
    void testMainInputMismatch() {
        testMainChoice("wrongInput\n0\n", "Incorrect option! Try again.\n" + "Bye!");
    }

    private void testMainChoice(String choices, String expectedOutput) {
        String numberOfPerson = "6\n";
        String people = "Dwight Joseph djo@gmail.com\n" +
                "Rene Webb webb@gmail.com\n" +
                "Katie Jacobs\n" +
                "Erick Harrington harrington@gmail.com\n" +
                "Myrtle Medina\n" +
                "Erick Burgess\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream((numberOfPerson + people + choices).getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);
        Stage3.main(new String[]{});
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }
}
