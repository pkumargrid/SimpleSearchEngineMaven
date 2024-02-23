import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteImplementationTest {

    @Test
    void findAll() {
        Map<String, Set<Integer>> invertedIndex;
        ArrayList<String> listOfPerson = new ArrayList<>(Arrays.asList(
                "Dwight Joseph djo@gmail.com",
                "Rene Webb webb@gmail.com",
                "Katie Jacobs",
                "Erick Harrington harrington@gmail.com",
                "Myrtle Medina",
                "Erick Burgess"
        ));
        invertedIndex = Stage6.createInvertedIndex(listOfPerson);
        Set<String> expectedResult = new HashSet<>(List.of(
                "Erick Harrington harrington@gmail.com"
        ));
        Set<String> actualResult = ConcreteImplementation.findAll("erick harrington", invertedIndex, listOfPerson);
        assertEquals(expectedResult, actualResult, "Should return matching person details");
    }

    @Test
    void findAllNotFound() {
        Map<String, Set<Integer>> invertedIndex;
        ArrayList<String> listOfPerson = new ArrayList<>(Arrays.asList(
                "Dwight Joseph djo@gmail.com",
                "Rene Webb webb@gmail.com",
                "Katie Jacobs",
                "Erick Harrington harrington@gmail.com",
                "Myrtle Medina",
                "Erick Burgess"
        ));
        invertedIndex = Stage6.createInvertedIndex(listOfPerson);
        Set<String> expectedResult = new HashSet<>(List.of(
                "No matching person found."
        ));
        Set<String> actualResult = ConcreteImplementation.findAll("pratik kumar", invertedIndex, listOfPerson);
        assertEquals(expectedResult, actualResult, "Should return matching person details");
    }

    @Test
    void findAny() {
        Map<String, Set<Integer>> invertedIndex;
        ArrayList<String> listOfPerson = new ArrayList<>(Arrays.asList(
                "Dwight Joseph djo@gmail.com",
                "Rene Webb webb@gmail.com",
                "Katie Jacobs",
                "Erick Harrington harrington@gmail.com",
                "Myrtle Medina",
                "Erick Burgess"
        ));
        invertedIndex = Stage6.createInvertedIndex(listOfPerson);
        Set<String> expectedResult = new HashSet<>(List.of(
                "Erick Harrington harrington@gmail.com",
                "Erick Burgess"
        ));
        Set<String> actualResult = ConcreteImplementation.findAny("erick burgess", invertedIndex, listOfPerson);
        assertEquals(expectedResult, actualResult, "Should return matching person details");
    }

    @Test
    void findAnyNotFound() {
        Map<String, Set<Integer>> invertedIndex;
        ArrayList<String> listOfPerson = new ArrayList<>(Arrays.asList(
                "Dwight Joseph djo@gmail.com",
                "Rene Webb webb@gmail.com",
                "Katie Jacobs",
                "Erick Harrington harrington@gmail.com",
                "Myrtle Medina",
                "Erick Burgess"
        ));
        invertedIndex = Stage6.createInvertedIndex(listOfPerson);
        Set<String> expectedResult = new HashSet<>(List.of(
                "No matching person found."
        ));
        Set<String> actualResult = ConcreteImplementation.findAny("pratik kumar", invertedIndex, listOfPerson);
        assertEquals(expectedResult, actualResult, "Should return matching person details");
    }

    @Test
    void findNone() {
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();
        ArrayList<String> listOfPerson = new ArrayList<>(Arrays.asList(
                "Dwight Joseph djo@gmail.com",
                "Rene Webb webb@gmail.com",
                "Katie Jacobs",
                "Erick Harrington harrington@gmail.com",
                "Myrtle Medina",
                "Erick Burgess"
        ));
        invertedIndex = Stage6.createInvertedIndex(listOfPerson);
        Set<String> expectedResult = new HashSet<>(List.of(
                "Dwight Joseph djo@gmail.com",
                "Rene Webb webb@gmail.com",
                "Katie Jacobs",
                "Myrtle Medina"
        ));
        Set<String> actualResult = ConcreteImplementation.findNone("erick burgess", invertedIndex, listOfPerson);
        assertEquals(expectedResult, actualResult, "Should return matching person details");
    }
    @Test
    void findNoneNotFound() {
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();
        ArrayList<String> listOfPerson = new ArrayList<>(Arrays.asList(
                "Dwight Joseph djo@gmail.com",
                "Rene Webb webb@gmail.com",
                "Katie Jacobs",
                "Erick Harrington harrington@gmail.com",
                "Myrtle Medina",
                "Erick Burgess"
        ));
        invertedIndex = Stage6.createInvertedIndex(listOfPerson);
        Set<String> expectedResult = new HashSet<>(List.of(
                "No matching person found."
        ));
        Set<String> actualResult = ConcreteImplementation.findNone("erick medina jacobs rene dwight kumar", invertedIndex, listOfPerson);
        assertEquals(expectedResult, actualResult, "Should return matching person details");
    }
}