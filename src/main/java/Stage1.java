import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

public class Stage1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] user_input = scanner.nextLine().split(" ");
        String to_search = scanner.nextLine();
        Optional<Integer> op = Stream.iterate(0,idx -> idx + 1).limit(user_input.length).filter(idx-> to_search.equals(user_input[idx])).findFirst();
        op.ifPresentOrElse((x) -> System.out.println(x + 1),()->System.out.println("Not found"));
    }
}
