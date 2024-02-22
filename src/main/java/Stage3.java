import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * In this stage,
 * you need to create a menu.
 * The menu should display the following options:
 * 1. Search information.
 * 2. Print all data.
 * 0. Exit.
 * The user must select a menu item and
 * then enter data if necessary.
 * Your program must not stop until the
 * corresponding option (the exit option) is chosen.
 * Decompose the program into separate methods to make it easy to understand
 * and to further develop or edit.
 */
public final class Stage3 {

    /**
     * private constructor for Utility class.
     */

    private Stage3() {

    }


    /**
     * method to print the options.
     * */
    public static void print() {
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }
    /**
     * @param args
     * main method working as per the options provided by user.
     * */
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.println("Enter the number of people:");
        int noOfPerson = Integer.parseInt(scanner.nextLine());
        ArrayList<String> listOfPerson = new ArrayList<>();
        System.out.println("Enter all people:");
        while (noOfPerson-- > 0) {
            listOfPerson.add(scanner.nextLine());
        }
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
                    Help.choice1(scanner, listOfPerson);
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
            }
        }

    }
}
