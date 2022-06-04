import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Bag bag = new Bag();
    private final Board board = new Board();
    private final Dictionary dictionary = new MockDictionary();
    private final List<Player> players = new ArrayList<>();
    private Thread timekeeper;
    private long startTime;
    private int activePlayer = 0;

    public static void main(String[] args) {
        Game game = new Game();

        game.addPlayer(new Player("Player 1"));
        game.addPlayer(new Player("Player 2"));
        game.addPlayer(new Player("Player 3"));

        game.play();
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }

    public void play() {
        startTime = System.nanoTime();

        timekeeper = new Thread(new Timekeeper());
        timekeeper.setDaemon(true);
        timekeeper.start();

        for (Player player : players) {
            new Thread(player).start();
        }
    }

    public Thread getTimekeeper() {
        return timekeeper;
    }

    public long getDuration() {
        return (System.nanoTime() - startTime) / 1000000;
    }

    public Bag getBag() {
        return bag;
    }

    public Board getBoard() {
        return board;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setNextPlayer() {
        activePlayer++;

        if (activePlayer == players.size())
            activePlayer = 0;
    }

    public int getActivePlayer() {
        return activePlayer;
    }
}
