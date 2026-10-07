import java.util.concurrent.Semaphore;

public class SemaphorePractice {

    private static Semaphore semaphore;
    private static int var;

    static {
        semaphore = new Semaphore(1);
        var = 0;
    }

    public static void practice() throws InterruptedException {
        Runnable incrementor = () -> {
            while (true) {
                if (semaphore.tryAcquire()) {
                    ++var;
                    semaphore.release();
                }
            }
        };
        Runnable checker = () -> {
            while (true) {
                if (semaphore.tryAcquire()) {
                    if (var % 100 == 17) System.out.println(var);
                    semaphore.release();
                }
            }
        };

        Thread th1 = new Thread(incrementor);
        Thread th2 = new Thread(checker);

        th1.start();
        th2.start();

        th1.join();
        th2.join();
    }
}
