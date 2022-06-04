import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bag {
    private final List<Tile> letters = new ArrayList<>(); // declare a collection for tiles

    public Bag() {
        addTile(new Tile('a', 1), 9);
        addTile(new Tile('b', 3), 2);
        addTile(new Tile('c', 3), 2);
        addTile(new Tile('d', 2), 4);
        addTile(new Tile('e', 1), 12);
        addTile(new Tile('f', 4), 2);
        addTile(new Tile('g', 2), 3);
        addTile(new Tile('h', 4), 2);
        addTile(new Tile('i', 1), 9);
        addTile(new Tile('j', 8), 1);
        addTile(new Tile('k', 5), 1);
        addTile(new Tile('l', 1), 4);
        addTile(new Tile('m', 3), 2);
        addTile(new Tile('n', 1), 6);
        addTile(new Tile('o', 1), 8);
        addTile(new Tile('p', 3), 2);
        addTile(new Tile('q', 10), 1);
        addTile(new Tile('r', 1), 6);
        addTile(new Tile('s', 1), 4);
        addTile(new Tile('t', 1), 6);
        addTile(new Tile('u', 1), 4);
        addTile(new Tile('v', 4), 2);
        addTile(new Tile('w', 4), 2);
        addTile(new Tile('x', 8), 1);
        addTile(new Tile('y', 4), 2);
        addTile(new Tile('z', 10), 1);
    }

    private void addTile(Tile tile, int number) {
        for (int i = 0; i < number; i++) {
            letters.add(tile);
        }
    }

    public List<Tile> getLetters() {
        return letters;
    }

    public synchronized List<Tile> extractTiles(int howMany) {
        List<Tile> extracted = new ArrayList<>();

        for (int i = 0; i < howMany; i++) {
            if (letters.isEmpty()) {
                break;
            }

            int rand = new Random().nextInt(letters.size());

            extracted.add(letters.get(rand));
            letters.remove(rand);
        }

        return extracted;
    }
}
