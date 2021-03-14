package TaskOneWithPhaserVar2;

import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;

public class TaskOneWithPhaserVar2 {
    private static final String INPUT = "OOHHHHOOHHHH";

    public static void main(String[] args) throws Exception {
        int numberOfMolecules = INPUT.length() / 3;
        Phaser phaser = new Phaser(1){
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("\nMolecule's done");
                if (phase == numberOfMolecules || registeredParties == 0) {
                    return true;
                }
                return false;
            }
        };

        System.out.println("Start making water");

        new WaterMaker(phaser, () -> new Hydrogen().releaseHydrogen()).start();
        new WaterMaker(phaser, () -> new Hydrogen().releaseHydrogen()).start();
        new WaterMaker(phaser, () -> new Oxygen().releaseOxygen()).start();

        while (!phaser.isTerminated()) {
            phaser.arriveAndAwaitAdvance();
        }

        System.out.println("Water is ready!");
    }
}

class WaterMaker extends Thread {
    private final Phaser phaser;
    private String name;
    private Callable<String> element;

    public WaterMaker(Phaser phaser, Callable<String> element) throws Exception {
        this.phaser = phaser;
        this.element = element;
        this.name = element.call();
        phaser.register();
    }

    @Override
    public void run() {
        while (!phaser.isTerminated()) {
            System.out.print(name);
            phaser.arriveAndAwaitAdvance();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Hydrogen implements Callable<String> {
    private final String name = "H";

    public String releaseHydrogen() {
        return name;
    }

    @Override
    public String call() throws Exception {
        return name;
    }
}

class Oxygen implements Callable<String> {
    private final String name = "O";

    public String releaseOxygen() {
        return name;
    }

    @Override
    public String call() throws Exception {
        return name;
    }
}
