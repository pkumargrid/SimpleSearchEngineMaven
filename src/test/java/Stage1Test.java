import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class Stage1Test {

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
        String userInputString = "apple banana orange";
        String searchTerm = "banana\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                (userInputString + System.lineSeparator() + searchTerm).getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);
        Stage1.main(new String[]{});
        String actualOutput = outputStream.toString().trim();
        assertEquals("2", actualOutput);
    }
    @Test
    void testMainNotFound() {
        String userInputString = "apple banana orange";
        String searchTerm = "grape\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                (userInputString + System.lineSeparator() + searchTerm).getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);
        Stage1.main(new String[]{});
        String actualOutput = outputStream.toString().trim();
        assertEquals("Not found", actualOutput);
    }
}