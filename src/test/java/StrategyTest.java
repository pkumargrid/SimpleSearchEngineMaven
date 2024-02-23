import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StrategyTest {

    private Map<String, Set<Integer>> invertedIndex;
    private ArrayList<String> listOfPerson;

    @Test
    void testStrategyImplementation() {
        listOfPerson = new ArrayList<>(Arrays.asList(
                "Dwight Joseph djo@gmail.com",
                "Rene Webb webb@gmail.com",
                "Katie Jacobs",
                "Erick Harrington harrington@gmail.com",
                "Myrtle Medina",
                "Erick Burgess"
        ));
        invertedIndex = Stage6.createInvertedIndex(listOfPerson);
        testImplementation1(ConcreteImplementation::findAll);
        testImplementation1(ConcreteImplementation::findAny);
        testImplementation2(ConcreteImplementation::findNone);
    }

    private void testImplementation1(Strategy strategy) {
        Set<String> expectedResult = new HashSet<>(Arrays.asList(
                "Erick Harrington harrington@gmail.com",
                "Erick Burgess"
        ));
        Set<String> actualResult = strategy.find("erick", invertedIndex, listOfPerson);
        assertEquals(expectedResult, actualResult, "Should return matching person details");
    }

    private void testImplementation2(Strategy strategy) {
        Set<String> expectedResult = new HashSet<>(Arrays.asList(
                "Dwight Joseph djo@gmail.com",
                "Rene Webb webb@gmail.com",
                "Katie Jacobs",
                "Myrtle Medina"
        ));
        Set<String> actualResult = strategy.find("erick", invertedIndex, listOfPerson);
        assertEquals(expectedResult, actualResult, "Should return matching person details");
    }
}
