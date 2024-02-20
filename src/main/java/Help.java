
import java.util.Scanner;
import java.util.ArrayList;
/**
 * Helps in resolving common issue while taking choice or displaying info.
 */
public class Help {

    /**
     *
     * @param scanner to take input from user.
     * @return the required choice
     */
    public static int getChoice(Scanner scanner) {
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Incorrect option! Try again.");
        }
        return choice;
    }

    /**
     *
     * @return true to mark that the user wants to exit
     */
    public static boolean choice0() {
        System.out.println("Bye!");
        return true;
    }

    public static void choice2(ArrayList<?> listOfPerson) {
        System.out.println("=== List of people ===");
        listOfPerson.forEach(System.out::println);
    }

    /**
     *
     * @param scanner to take the word that user wants to search
     * @param listOfPerson list of person in which word has to be searched
     */
    public static void choice1(Scanner scanner, ArrayList<String> listOfPerson) {
        System.out.println("Enter a name or email to search all suitable people.");
        String toSearch = scanner.nextLine();
        for (String person : listOfPerson) {
            if (person.toLowerCase()
                    .contains(toSearch.toLowerCase())) {
                System.out.println(person);
            }
        }
    }
}
