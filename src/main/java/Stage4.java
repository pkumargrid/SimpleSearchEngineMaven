import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Stage4 {
    public static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
    public static void print(){
        System.out.println("==Menu==");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }
    public static void main(String[] args) throws Exception{
        String fileName = args[1];
        String people = readFile(fileName);
        ArrayList<String> list_of_person = new ArrayList<>();
        Scanner sc = new Scanner(people);
        while(sc.hasNext()){
            list_of_person.add(sc.nextLine());
        }
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while(!exit){
            print();
            int choice = -1;
            try{
                choice = Integer.parseInt(scanner.nextLine());
            }
            catch(Exception e){
                System.out.println("Incorrect option! Try again.");
                continue;
            }
            switch(choice){
                case 0:
                    System.out.println("Bye!");
                    exit = true;
                    break;
                case 2:
                    System.out.println("=== List of people ===");
                    list_of_person.forEach(System.out::println);
                    break;
                case 1 :
                    System.out.println("Enter a name or email to search all suitable people.");
                    String toSearch = scanner.nextLine();
                    for(String p : list_of_person){
                        if(p.toLowerCase().contains(toSearch.toLowerCase())){
                            System.out.println(p);
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
