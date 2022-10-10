import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;


public class RobotRace {

    static CyclicBarrier gate = null;
    static void displayThreadMessage(String message){
        System.out.println(message);
    }

    // method for converting time in seconds
    private static float getTimeInSeconds() throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 60; i++) {
            Thread.sleep(60);
        }
        long end = System.currentTimeMillis();
        float sec = (end - start) / 1000F;
        return sec;
    }


    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        // declare a milli seconds variable for delaying
        long milliSeconds = 1000 * 60 * 60;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        long startTime = System.currentTimeMillis();

        displayThreadMessage("New race started at " + formatter.format(currentTime));

        Robot[] robots = new Robot[]{
                new Robot("Robot", 1),
                new Robot("Robot", 2),
                new Robot("Robot", 3),
                new Robot("Robot", 4),
                new Robot("Robot", 5),
                new Robot("Robot", 6),
                new Robot("Robot", 7),
                new Robot("Robot", 8),
                new Robot("Robot", 9),
                new Robot("Robot", 10),
        };

        Thread[] threads = new Thread[robots.length];

        gate = new CyclicBarrier(robots.length + 1);

        for (int i = 0; i < robots.length; i++) {
            threads[i] = new Thread(robots[i]);
            threads[i].start();
        }
        gate.await();
        // loop until the last robot finishes
        for (int i = 0; i < robots.length; i++) {
            while (threads[i].isAlive()) {
                displayThreadMessage("Waiting...");
                // the join here wait for another thread to finish
                threads[i].join(1000);
                if (((System.currentTimeMillis() - startTime) > milliSeconds)
                        && threads[i].isAlive()) {
                    displayThreadMessage("I can't wait anymore!");
                    threads[i].interrupt();
                    threads[i].join();
                }
            }
        }
        displayThreadMessage("Race finished at " + formatter.format(System.currentTimeMillis()));
        // when the race ends, show all the robots in order
        for (int i = 0; i < robots.length; i++) {
            displayThreadMessage(robots[i].getName() + " " + (i + 1) + " finishes in " + getTimeInSeconds() + " seconds");
        }

    }
}