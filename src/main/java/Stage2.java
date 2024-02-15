import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Stage2 {
    private static List<String> getString(String word, ArrayList<String> list_of_persons) {
        List<String> matching_lines = new ArrayList<>();
        for (String s : list_of_persons) {
            if (s.contains(word)) {
                matching_lines.add(s);
            }
        }
        return matching_lines;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of people:");
        int noOfPerson = Integer.parseInt(scanner.nextLine());
        ArrayList<String> list_of_person = new ArrayList<>();
        System.out.println("Enter all people:");
        while(noOfPerson-- > 0){
            list_of_person.add(scanner.nextLine());
        }
        System.out.println("Enter the number of search queries:");
        int queries = Integer.parseInt(scanner.nextLine());
        while(queries-- > 0){
            System.out.println("Enter data to search people:");
            String to_search = scanner.nextLine();
            List<String> find = getString(to_search.toLowerCase(), list_of_person);
            if(find.isEmpty()){
                System.out.println("No matching people found.");
            }
            else{
                System.out.println("Found people:");
                find.forEach(System.out::println);
            }
        }
    }
}
