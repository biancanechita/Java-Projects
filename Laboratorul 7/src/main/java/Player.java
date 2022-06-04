import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Player implements Runnable {
    private final String name;
    private Game game;
    private boolean running = true;
    private List<Tile> extracted = new ArrayList<>();
    private int howMany = 7;
    private int score = 0;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private void submitWord() {
        System.out.println(name + "'s turn");

        System.out.println("You will extract " + howMany + " tiles");

        if (game.getBag().getLetters().size() < howMany) {
            System.out.println("Bag has not enough tiles!");

            howMany = game.getBag().getLetters().size();
        }

        extracted.addAll(game.getBag().extractTiles(howMany));

        List<Tile> copyOfExtracted = new ArrayList<>(extracted);

        boolean key = true;

        String word = null;
        int points = 0;

        while (key) {
            extracted = new ArrayList<>();
            extracted.addAll(copyOfExtracted);

            System.out.println("You have tiles: " + extracted);
            System.out.print("Type here: ");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            points = 0;

            try {
                word = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            boolean foundAllLetters = true;

            for (int i = 0; i < word.length() && foundAllLetters; i++) {
                boolean letterIsFound = false;

                for (Tile tile : extracted) {
                    if (word.charAt(i) == tile.getLetter()) {
                        letterIsFound = true;
                        extracted.remove(tile);

                        points = points + tile.getPoints();
                        break;
                    }
                }

                if (!letterIsFound) {
                    foundAllLetters = false;
                }
            }

            if (foundAllLetters) {
                key = false;
            } else {
                System.out.println("You must use the tiles extracted!");
            }
        }

        if (game.getDictionary().isWord(word)) {
            game.getBoard().addWord(this, word);

            points = points * word.length();
            System.out.println("You gained: " + points + " points.");

            score = score + points;
            System.out.println("Your total score is: " + score);

            howMany = word.length();
        } else {
            howMany = 0;

            extracted = new ArrayList<>();
            extracted.addAll(copyOfExtracted);

            System.out.println("Word expected! You received 0 points!");
        }

        // make the player sleep 50 ms
        try {
            sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (game.getBag().getLetters().isEmpty()) {
            running = false;
        }
    }

    @Override
    public void run() {
        while (running) {
            waitForOthers(game.getPlayers().indexOf(this));     // players wait their turns, using a wait-notify approach

            submitWord();

            if (game.getTimekeeper().isAlive()) {
                System.out.println("Time reached: " + game.getDuration() + " millis");  // display the running time of the game
                game.setNextPlayer();
            } else {
                System.out.println("Time limit hab been reached!");
                running = false;    // it will stop the game if it exceeds a certain time limit
            }
        }

        System.out.println("Game has ended!");

        Player winner = game.getPlayers().get(0);

        for (Player player : game.getPlayers()) {
            if (player.score > winner.score)
                winner = player;
        }

        System.out.println(winner.getName() + " has won the game!");
    }

    private void waitForOthers(int indexOf) {
        synchronized (game) {
            game.notifyAll();

            while (game.getActivePlayer() != indexOf) {
                try {
                    game.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
