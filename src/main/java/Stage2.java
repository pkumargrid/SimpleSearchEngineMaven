import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A program that reads text lines from the standard input
 *          and processes single-word queries.
 *  The program must output all lines that contain the string
 *          from the query.
 *  For this stage, this should include the case
 *      where the query string appears as a substring of one of the text lines.
 *  For example, the query "bc" should be found in a line containing "abcd".
 */
public final class Stage2 {

    /**
     * private constructor for Utility class.
     */
    private Stage2() {

    }


    /**
     *
     * @param toSearch word to search for
     * @param listOfPerson array in which we have to look for the word
     * @return returns matching documents in form of list<String>
     *
     */
    private static List<String> getWordsThatMatch(
            final String toSearch, final ArrayList<String> listOfPerson) {
        List<String> matchingLines = new ArrayList<>();
        for (String person : listOfPerson) {
            if (person.contains(toSearch)) {
                matchingLines.add(person);
            }
        }
        return matchingLines;
    }

    /**
     * @param args to get command line arguments
     */

    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.println("Enter the number of people:");
        int noOfPerson = Integer.parseInt(scanner.nextLine());
        ArrayList<String> listOfPerson = new ArrayList<>();
        System.out.println("Enter all people:");
        while (noOfPerson-- > 0) {
            listOfPerson.add(scanner.nextLine());
        }
        System.out.println("Enter the number of search queries:");
        int queries = Integer.parseInt(scanner.nextLine());
        while (queries-- > 0) {
            System.out.println("Enter data to search people:");
            String wordToSearch = scanner.nextLine();
            List<String> find =
                    getWordsThatMatch(wordToSearch.toLowerCase(), listOfPerson);
            if (find.isEmpty()) {
                System.out.println("No matching people found.");
            } else {
                System.out.println("Found people:");
                find.forEach(System.out::println);
            }
        }
    }
}

