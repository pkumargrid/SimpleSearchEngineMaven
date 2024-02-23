import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        System.out.println("=== Menu ===");
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
        String charsetName = "UTF-8";
        byte[] bytes = people.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        Scanner scanner = new Scanner(inputStream, charsetName);
        while (scanner.hasNext()) {
            listOfPerson.add(scanner.nextLine());
        }
        scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        boolean exit = false;
        while (!exit) {
//            print();
            exit = isExit(listOfPerson, scanner, exit);
        }
        scanner.close();
    }

    /**
     *
     * @param listOfPerson represents the database
     * @param scanner for reading input from user
     * @param exit to mark exit
     * @return the boolean value of exit
     */

    static boolean isExit(ArrayList<String> listOfPerson, Scanner scanner, boolean exit) {
        int choice = Help.getChoice(scanner);
        switch (choice) {
            case 0 :
                exit = Help.choice0();
                break;
            case 2 :
                Help.choice2(listOfPerson);
                break;
            case 1 :
                Help.choice1(scanner, listOfPerson);
                break;
            default:
                System.out.println("Incorrect option! Try again.");
        }
        return exit;
    }
}
