import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The program can successfully search for all matching lines.
 * And the search is case- and space-insensitive.
 * Problem handled: Need to check each line to find out whether it contains the query string.
 * To optimize the program,
 * used data structure called an Inverted Index.
 * It maps each word to all positions/lines/documents in which the word occurs.
 * As a result, when we receive a query,
 * we can immediately find the answer without any comparisons.
 */

public final class Stage5 {

    /**
     * private constructor for Utility class.
     */
    private Stage5() {

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
     * @param args command line arguments
     * @throws Exception throws the exception thrown
     * by readFile method
     * main method encapsulating all operations,
     * helps in finding the query word in list of documents
     * using inverted index
     */
    public static void main(final String[] args) throws Exception {
        String fileName = args[1];
        String text = readFile(fileName);
        ArrayList<String> listOfPerson = new ArrayList<>();
        Scanner scanner = new Scanner(text);
        while (scanner.hasNext()) {
            listOfPerson.add(scanner.nextLine());
        }
        Map<String, ArrayList<Integer>> invertedIndex = new HashMap<>();
        for (int i = 0; i < listOfPerson.size(); i++) {
            String[] personDetails = listOfPerson.get(i).split(" ");
            for (String personDetail : personDetails) {
                invertedIndex.putIfAbsent(personDetail, new ArrayList<>());
                invertedIndex.get(personDetail).add(i);
            }
        }
        scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            print();
            int choice = Help.getChoice(scanner);
            if (choice == -1) {
                continue;
            }
            switch (choice) {
                case 0 :
                    Help.choice0();
                    break;
                case 2 :
                    Help.choice2(listOfPerson);
                    break;
                case 1 :
                    System.out.println("Enter a name or email to search all suitable people.");
                    String toSearch = scanner.nextLine();
                    if (invertedIndex.get(toSearch) != null) {
                        ArrayList<Integer> indices = invertedIndex.get(toSearch);
                        for (int index : indices) {
                            System.out.println(listOfPerson.get(index));
                        }
                    } else {
                        System.out.println("No matching people found.");
                    }
                    break;
                default :
                    System.out.println("Incorrect option! Try again.");
            }
        }
        scanner.close();

    }
}
