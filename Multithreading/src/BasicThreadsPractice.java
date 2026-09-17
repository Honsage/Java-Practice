public class BasicThreadsPractice {
    public static void practice() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        final Thread thread0 = new MyThread();
        thread0.start();

        final Thread thread1 = anonymousClassThread();
        thread1.start();

        final Thread thread2 = threadByRunnable();
        thread2.start();
    }

    private static Thread anonymousClassThread() {
        return new Thread() {
            @Override
            public void run() {
                System.out.println(currentThread().getName());
            }
        };
    }

    private static Thread threadByRunnable() {
        final Runnable task = () -> {
            System.out.println(Thread.currentThread().getName());
        };
        return new Thread(task);
    }

    private static final class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println(currentThread().getName());
        }
    }
}
