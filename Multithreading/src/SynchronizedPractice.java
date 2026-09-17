import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class SynchronizedPractice {

    private static final Object state;

    static {
        class State {
            private int s;

            @Override
            public String toString() {
                s += (new Random().nextBoolean()) ? 1 : -1;
                return String.valueOf(s);
            }
        }

        state = new State();
    }

    public static void practice() throws InterruptedException {
        Runnable task = () -> {
            synchronized (SynchronizedPractice.state) {
                String threadName = Thread.currentThread().getName();
                IntStream.range(0, 30).forEach(_ ->
                        System.out.println(threadName + " s: " + state)
                );
            }
        };
        Thread th1 = new Thread(task);
        Thread th2 = new Thread(task);

        th1.start();
        th2.start();

        th1.join();
        th2.join();
    }
}
