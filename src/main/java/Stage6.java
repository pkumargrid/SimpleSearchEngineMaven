import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


/**
 * this interface is used for defining the common strategy
 * It helps in defining the functional interface to override
 * for defining explicit functionality.
 */

interface Strategy {

    /**
     *
     * @param toSearch to search word
     * @param invertedIndex inverted mapping
     * @param listOfPerson list of person details
     * @return set of lines where did the query match
     */

    Set<String> find(String toSearch, Map<String, Set<Integer>> invertedIndex, ArrayList<String> listOfPerson);

}

/**
 * This class gives explicit implementation of strategy.
 * strategies: all, any, none
 * All - the program should print
 *          lines containing all the words from the query.
 * Any - the program should print the
 *          lines containing at least one word from the query.
 * None - the program should print lines
 *      that do not contain words from the query at all
 */

final class ConcreteImplementation {

    /**
     * private constructor for Utility class.
     */

    private ConcreteImplementation() {

    }

    /**
     *
     * @param toSearch word to search
     * @param invertedIndex contains mapping from index to document
     * @param listOfPerson contains list of person details
     * @return returns list of index of documents as per all strategy.
     */

    public static Set<String> findAll(final String toSearch, final Map<String, Set<Integer>> invertedIndex, final ArrayList<String> listOfPerson) {
        Set<Integer> lineIndex = new HashSet<>();
        boolean firstTimeEntering = true;
        for (String personDetail : toSearch.split(" ")) {
            if (invertedIndex.get(personDetail) == null) {
                continue;
            }
            personDetail = personDetail.toLowerCase();
            if (firstTimeEntering) {
                firstTimeEntering = false;
                lineIndex.addAll(invertedIndex.get(personDetail));
            } else {
                Set<Integer> temp = new HashSet<>();
                for (int index : invertedIndex.get(personDetail)) {
                    if (lineIndex.contains(index)) {
                        temp.add(index);
                    }
                }
                lineIndex = temp;
            }
        }
        if (lineIndex.isEmpty()) {
            return new HashSet<>(List.of("No matching person found."));
        } else {
            return lineIndex.stream().map(listOfPerson::get).collect(Collectors.toSet());
        }
    }

    /**
     *
     * @param toSearch word to search
     * @param invertedIndex contains mapping from index to document
     * @param listOfPerson contains list of person details
     * @return returns list of index of documents as per any strategy.
     */

    public static Set<String> findAny(final String toSearch, final Map<String, Set<Integer>> invertedIndex, final ArrayList<String> listOfPerson) {
        Set<Integer> lineIndex = new HashSet<>();
        for (String personDetail : toSearch.split(" ")) {
            personDetail = personDetail.toLowerCase();
            if (invertedIndex.get(personDetail) == null) {
                continue;
            }
            lineIndex.addAll(invertedIndex.get(personDetail));
        }
        if (lineIndex.isEmpty()) {
            return new HashSet<>(List.of("No matching person found."));
        } else {
            return lineIndex.stream().map(listOfPerson::get).collect(Collectors.toSet());
        }
    }

    /**
     *
     * @param toSearch word to search
     * @param invertedIndex contains mapping from index to document
     * @param listOfPerson contains list of person details
     * @return returns list of index of documents as per none strategy.
     *
     */

    public static Set<String> findNone(final String toSearch, final Map<String, Set<Integer>> invertedIndex, final ArrayList<String> listOfPerson) {
        Set<Integer> lineIndex = new HashSet<>();
        Set<Integer> temp = new HashSet<>();
        for (String personDetail : toSearch.split(" ")) {
            personDetail = personDetail.toLowerCase();
            if (invertedIndex.get(personDetail) == null) {
                continue;
            }
            temp.addAll(invertedIndex.get(personDetail));
        }
        for (int i = 0; i < listOfPerson.size(); i++) {
            if (!temp.contains(i)) {
                lineIndex.add(i);
            }
        }
        if (lineIndex.isEmpty()) {
            return new HashSet<>(List.of("No matching person found."));
        } else {
            return lineIndex.stream().map(listOfPerson::get).collect(Collectors.toSet());
        }
    }

}

/**
 * Main class that contains the implementation part.
 */

public final class Stage6 {

    /**
     * private constructor for Utility class.
     */

    private Stage6() {

    }

    /**
     *
     * @param path path of file to read for
     * @return string (containing list of person details)
     * @throws IOException shows this method throws a checked
     * exception
     * this method helps in reading the list of persons from file
     */

    public static String readFile(final String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    /**
     * displays the options available for user to select.
     */

    public static void print() {
        System.out.println("==Menu==");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }

    /**
     *
     * @param listOfPerson contains list of person details (input)
     * @return inverted mapping of given list
     */

    public static Map<String, Set<Integer>> createInvertedIndex(final ArrayList<String> listOfPerson) {
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();
        for (int i = 0; i < listOfPerson.size(); i++) {
            String[] persons = listOfPerson.get(i).split(" ");
            for (String person : persons) {
                person = person.toLowerCase();
                invertedIndex.putIfAbsent(person, new HashSet<>());
                invertedIndex.get(person).add(i);
            }
        }
        return invertedIndex;
    }

    /**
     *
     * @param args command line argument
     * @throws Exception thrown by readFile method is thrown for jvm
     * to handle.
     */

    public static void main(final String[] args) throws Exception {
        String fileName = args[1];
        String txt = readFile(fileName);
        ArrayList<String> listOfPerson = new ArrayList<>();
        Scanner sc = new Scanner(txt);
        while (sc.hasNext()) {
            listOfPerson.add(sc.nextLine());
        }
        Map<String, Set<Integer>> invertedIndex = createInvertedIndex(listOfPerson);
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        boolean exit = false;
        while (!exit) {
            print();
            int choice = Help.getChoice(scanner);
            switch (choice) {
                case 0 :
                    exit = Help.choice0();
                    break;
                case 2 :
                    Help.choice2(listOfPerson);
                    break;
                case 1 :
                    System.out.println("Select a matching strategy: ALL, ANY, NONE");
                    String strategy = scanner.nextLine();
                    System.out.println("Enter a name or email to search all suitable people.");
                    String toSearch = scanner.nextLine();
                    Strategy strat = null;
                    if (strategy.equals("ALL")) {
                        strat = ConcreteImplementation::findAll;
                    } else if (strategy.equals("ANY")) {
                        strat = ConcreteImplementation::findAny;

                    } else {
                        strat = ConcreteImplementation::findNone;
                    }
                    strat.find(toSearch, invertedIndex, listOfPerson).forEach(System.out::println);
                    break;
                default :
                    System.out.println("Incorrect option! Try again.");
            }
        }
        scanner.close();
    }
}
