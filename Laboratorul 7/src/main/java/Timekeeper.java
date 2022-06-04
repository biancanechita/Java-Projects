import static java.lang.Thread.sleep;

public class Timekeeper implements Runnable {
    @Override
    public void run() {
        try {
            sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
