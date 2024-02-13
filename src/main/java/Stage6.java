import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Stage6 {
    public static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
    public static void print(){
        System.out.println("==Menu==");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }
    public static Set<String> find(String toSearch,String strategy, Map<String,Set<Integer>> inv_idx,ArrayList<String>arr){
        Set<Integer> line_index = new HashSet<>();
        if(strategy.equals("ALL")){
            boolean first_time_entering = true;
            for(String personDetail : toSearch.split(" ")){
                if(inv_idx.get(personDetail) == null) continue;
                personDetail = personDetail.toLowerCase();
                if(first_time_entering){
                    first_time_entering = false;
                    line_index.addAll(inv_idx.get(personDetail));
                }
                else{
                    Set<Integer> temp = new HashSet<>();
                    for(int idx : inv_idx.get(personDetail)){
                        if(line_index.contains(idx)){
                            temp.add(idx);
                        }
                    }
                    line_index = temp;
                }
            }
        }
        else if(strategy.equals("ANY")){
            for(String personDetail : toSearch.split(" ")){
                personDetail = personDetail.toLowerCase();
                if(inv_idx.get(personDetail) == null) continue;
                line_index.addAll(inv_idx.get(personDetail));
            }
        }
        else{
            Set<Integer> s = new HashSet<>();
            for(String personDetail : toSearch.split(" ")){
                personDetail = personDetail.toLowerCase();
                if(inv_idx.get(personDetail) == null){
                   continue;
                }
                s.addAll(inv_idx.get(personDetail));
            }
            for(int i = 0; i < arr.size(); i++){
                if(!s.contains(i)){
                    line_index.add(i);
                }
            }
        }
        if(line_index.isEmpty()){
            return new HashSet<>(List.of("No matching person found."));
        }
        else{
            return line_index.stream().map(arr::get).collect(Collectors.toSet());
        }
    }
    public static Map<String,Set<Integer>> create_inverted_index(ArrayList<String> arr){
        Map<String,Set<Integer>> inverted_index = new HashMap<>();
        for(int i = 0; i < arr.size(); i++){
            String[] words = arr.get(i).split(" ");
            for(String word : words){
                word = word.toLowerCase();
                inverted_index.putIfAbsent(word, new HashSet<>());
                inverted_index.get(word).add(i);
            }
        }
        return inverted_index;
    }
    public static void main(String[] args) throws Exception{
        String fileName = args[1];
        String txt = readFile(fileName);
        ArrayList<String> list_of_person = new ArrayList<>();
        Scanner sc = new Scanner(txt);
        while(sc.hasNext()){
            list_of_person.add(sc.nextLine());
        }
        Map<String,Set<Integer>> inverted_index
                = create_inverted_index(list_of_person);

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
                    System.out.println("Select a matching strategy: ALL, ANY, NONE");
                    String strategy = scanner.nextLine();
                    System.out.println("Enter a name or email to search all suitable people.");
                    String toSearch = scanner.nextLine();
                    find(toSearch,strategy,inverted_index,list_of_person).forEach(System.out::println);
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
            }
        }
        scanner.close();

    }
}
