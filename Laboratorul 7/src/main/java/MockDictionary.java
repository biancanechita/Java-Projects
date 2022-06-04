import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MockDictionary extends Dictionary {
    private final List<String> words = new ArrayList<>();

    public MockDictionary() {
        Trie.root = new TrieNode();

        try {
            File myObj = new File("C:\\Users\\nechi\\Documents\\GitHub\\Programare-Avansata\\Laboratorul 7\\src\\main\\resources\\MockDictionary.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Trie.insert(data.toLowerCase());    // Representing the words in the dictionary as a prefix tree,

                words.add(data);    // or using some collection of words
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public boolean isWord(String str) {
        long startTime = System.nanoTime();

        // Prefix search (for a classical collection) using a multi-threaded approach
        List<String> lookup = words.parallelStream().filter(s -> s.startsWith(str)).toList();

//        System.out.println("Prefix search: " + lookup);

        long endTime = System.nanoTime();

        System.out.println("The running time for the lookup operation, standard collection: " + (endTime - startTime) / 1000000 + " millis");

//        return lookup.contains(str);

        return Trie.search(str);
    }
}
