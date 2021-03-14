package TaskTwo;

import java.util.concurrent.*;

public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    public MyThreadPoolExecutor(int nThreads) {
        super(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    @Override
    public void execute(Runnable command) {
        if (!command.getClass().isAnnotationPresent(Repeat.class)) {
            super.execute(command);
        } else {
            Repeat annotation = command.getClass().getAnnotation(Repeat.class);
            int counter = annotation.value();
            while (counter-- > 0) {
                super.execute(command);
            }
        }
    }
}
