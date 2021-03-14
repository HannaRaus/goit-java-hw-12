package TaskOneWithPhaserVar1;

import java.util.concurrent.Phaser;

public class TaskOneWithPhaserVar1 {
    private static final String INPUT = "OOHHHHOOHHHH";

    public static void main(String[] args) {
        int numberOfMolecules = INPUT.length() / 3;
        MyPhaser phaser = new MyPhaser(1, numberOfMolecules);

        System.out.println("Start making water");
        new Water(phaser, "H").start();
        new Water(phaser, "H").start();
        new Water(phaser, "O").start();

        while (!phaser.isTerminated()) {
            phaser.arriveAndAwaitAdvance();
        }

        System.out.println("Water is ready!");
    }

}

class Water extends Thread {
    private final Phaser phaser;
    private String name;

    public Water(Phaser phaser, String name) {
        this.phaser = phaser;
        this.name = name;
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

class MyPhaser extends Phaser {
    private final int numberOfMolecules;

    MyPhaser(int parties, int numberOfMolecules) {
        super(parties);
        this.numberOfMolecules = numberOfMolecules - 1;
    }

    protected boolean onAdvance(int phase, int registeredParties) {
        System.out.println("\nMolecule's done");
        if (phase == numberOfMolecules || registeredParties == 0) {
            return true;
        }
        return false;
    }
}