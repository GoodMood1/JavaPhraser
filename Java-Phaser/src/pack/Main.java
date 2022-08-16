package pack;

import java.util.concurrent.Phaser;

public class Main {

    static volatile int n;

    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser();

        for (int i = 0; i < 3; i++) {
            phaser.register();
            for (int k = 0; k <= phaser.getPhase(); k++) {
                Thread t = new Thread(() -> {
                    System.out.println("Start " + Thread.currentThread().getName());
                    phaser.awaitAdvance(phaser.getPhase());
                    System.out.println("Last " + Thread.currentThread().getName());
                });

                t.setName("T" + n++);
                t.start();
            }
            Thread.sleep(2970);

            for (int j = 0, phase = phaser.getPhase(); j <= phase; j++) {
                phaser.arrive();
            }
        }
    }
}