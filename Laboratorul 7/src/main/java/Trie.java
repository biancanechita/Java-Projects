/**
 * Java implementation of search and insert operations on prefix tree
 */
public class Trie {
    /**
     * Alphabet size (# of symbols)
     */
    static final int ALPHABET_SIZE = 26;

    static TrieNode root;

    static void insert(String key) {
        int length = key.length();

        TrieNode pCrawl = root;

        for (int level = 0; level < length; level++) {
            int index = key.charAt(level) - 'a';

            if (pCrawl.children[index] == null)
                pCrawl.children[index] = new TrieNode();

            pCrawl = pCrawl.children[index];
        }

        // mark last node as leaf
        pCrawl.isEndOfWord = true;
    }

    static boolean search(String key) {
        long startTime = System.nanoTime();
        long endTime;

        int length = key.length();

        TrieNode pCrawl = root;

        for (int level = 0; level < length; level++) {
            int index = key.charAt(level) - 'a';

            if (pCrawl.children[index] == null) {
                endTime = System.nanoTime();
                System.out.println("The running time for the lookup operation, prefix tree: " + (endTime - startTime));

                return false;
            }

            pCrawl = pCrawl.children[index];
        }

        endTime = System.nanoTime();
        System.out.println("The running time for the lookup operation, prefix tree: " + (endTime - startTime) / 1000000 + " millis");

        return (pCrawl.isEndOfWord);
    }
}
