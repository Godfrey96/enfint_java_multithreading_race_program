import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

class Robot implements Runnable {
    private String name;
    private int number;

    static private int raceLength = 100;

    public Robot(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void run() {
        try {
            RobotRace.gate.await();
            while (raceLength < 200) {
                Thread.sleep(4000);
                raceLength += this.randomSpeed();
                if (raceLength < 200) {
                    RobotRace.displayThreadMessage(
                            name + " " + number + " moves forward by " + randomSpeed() + " units");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private int randomSpeed() {
        Random rand = new Random();
        int low = 1;
        int high = 4;
        return rand.nextInt(high - low) + low;
    }
}