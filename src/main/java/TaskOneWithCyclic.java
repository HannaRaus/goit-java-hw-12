
import java.util.concurrent.*;

/*
Есть два вида потоков, oxygen и hydrogen.

Ваша задача сгруппировать потоки и составить из них молекулы воды.
Должен быть барьер, где каждый поток ждет, пока не будет составлена молекула воды.

Для потоков должны быть методы releaseHydrogen и releaseOxygen, которые позволяют им "пройти барьер" и вывести в консоль Н или О.

Потоки обязательно проходят барьер только группами из 3 (2 гидрогена и 1 оксиген).
Так как после барьера идет вывод на консоль, убедитесь, что группа из 3 элементов была выведена до того,
как начнется вывод следующей группы (корректный вывод: НОН, ОНН, ННО, но не НООН - значит один атом оксигена попал из другой группы).

Другими словами:

если поток оксигена "подходит к барьеру", но нет потоков гидрогена, он ждет 2 потока гидрогена;
если поток гидрогена "подходит к барьеру" и нет других потоков, он ждет поток оксигена и еще один поток гидрогена.
 */
public class TaskOneWithCyclic {
    private static final String INPUT = "OOHHHH";
    public static void main(String[] args) {
        int numberOfMolecules = INPUT.length()/3;

        ExecutorService service = Executors.newFixedThreadPool(3);
        CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("\nmolecule's done"));

        for (int i = 0; i < numberOfMolecules; i++) {
            service.execute(new Hydrogen(barrier));
            service.execute(new Hydrogen(barrier));
            service.execute(new Oxygen(barrier));
        }
        service.shutdown();

    }

}

class Hydrogen implements Runnable{
    private final String name = "H";
    private final CyclicBarrier cyclicBarrier;

    public Hydrogen(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    private void releaseHydrogen() {
        System.out.print(name);
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        releaseHydrogen();
    }
}

class Oxygen implements Runnable{
    private final String name = "O";
    private final CyclicBarrier cyclicBarrier;

    public Oxygen(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    private void releaseOxygen() {
        System.out.print(name);
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        releaseOxygen();
    }
}

