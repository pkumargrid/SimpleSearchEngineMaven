import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class Stage2Test {

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
    void testMainFound() {
        String numberOfPerson = "6\n";
        String people = "Dwight Joseph djo@gmail.com\n"
                        + "Rene Webb webb@gmail.com\n"
                        + "Katie Jacobs\n"
                        + "Erick Harrington harrington@gmail.com\n"
                        + "Myrtle Medina\n"
                        + "Erick Burgess\n";
        String numberOfQueries = "1\n";
        String dataToSearch = "ERICK\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                (numberOfPerson + people + numberOfQueries + dataToSearch).getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);
        Stage2.main(new String[]{});
        String actualOutput = outputStream.toString().trim();
        String expectedOutput = "Found people:\nErick Harrington harrington@gmail.com\n" +
                "Erick Burgess";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testMainNotFound() {
        String numberOfPerson = "6\n";
        String people = "Dwight Joseph djo@gmail.com\n"
                + "Rene Webb webb@gmail.com\n"
                + "Katie Jacobs\n"
                + "Erick Harrington harrington@gmail.com\n"
                + "Myrtle Medina\n"
                + "Erick Burgess\n";
        String numberOfQueries = "1\n";
        String dataToSearch = "Bob\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                (numberOfPerson + people + numberOfQueries + dataToSearch).getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);
        Stage2.main(new String[]{});
        String actualOutput = outputStream.toString().trim();
        String expectedOutput = "No matching people found.";
        assertEquals(expectedOutput, actualOutput);
    }
}