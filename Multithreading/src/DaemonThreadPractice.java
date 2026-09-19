public class DaemonThreadPractice {

    public static void practice() throws InterruptedException {
        System.out.println(Thread.currentThread().isDaemon()); // false

        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    System.out.println(Thread.currentThread().getName() +
                            ": " + (Thread.currentThread().isDaemon() ? "is daemon" : "not daemon"));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        thread.setDaemon(true);
        thread.start();

        Thread.sleep(1000);
    }
}
