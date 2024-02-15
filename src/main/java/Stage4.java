import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Modified version of Stage3.
 * In this stage,
 *      the program reads
 *          data from a text file instead of the standard input.
 * The file structure will depend on your text data type
 *      (personal information,
 *      address information, book information, and so on).
 */
public final class Stage4 {

    /**
     * private constructor for Utility class.
     */

    private Stage4() {

    }

    /**
     *
     * @param fileName containing the list of person details.
     * @return string (containing list of person details)
     * @throws IOException tells that this function throws checked
     * exception
     *
     * this method helps in retrieving the list of people, from a file.
     */
    public static String readFile(final String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    /**
     * displays menu to user to choose from.
     */
    public static void print() {
        System.out.println("==Menu==");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }

    /**
     * @param args command line arguments
     * @throws Exception throws the exception passed on by
     * readFile method
     * main method to display info according to choice,
     * made by user from menu.
     */
    public static void main(final String[] args) throws Exception {
        String fileName = args[1];
        String people = readFile(fileName);
        ArrayList<String> listOfPerson = new ArrayList<>();
        Scanner sc = new Scanner(people);
        while (sc.hasNext()) {
            listOfPerson.add(sc.nextLine());
        }
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            print();
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Incorrect option! Try again.");
                continue;
            }
            switch (choice) {
                case 0 :
                    System.out.println("Bye!");
                    exit = true;
                    break;
                case 2 :
                    System.out.println("=== List of people ===");
                    listOfPerson.forEach(System.out::println);
                    break;
                case 1 :
                    System.out.println("Enter a name or email to search all suitable people.");
                    String toSearch = scanner.nextLine();
                    for (String person : listOfPerson) {
                        if (person.toLowerCase()
                                .contains(toSearch.toLowerCase())) {
                            System.out.println(person);
                        }
                    }
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
            }
        }
        scanner.close();
    }
}
