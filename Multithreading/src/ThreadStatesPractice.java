public class ThreadStatesPractice {

    public static void practice() throws InterruptedException {
        Thread mainThread = Thread.currentThread();

        Thread thread = new Thread(() -> {
            try {
                printThreadState(mainThread); // TIMED_WAITING
                Thread.sleep(10);
                printThreadState(mainThread); // WAITING
                printThreadState(Thread.currentThread()); // RUNNABLE
            } catch (InterruptedException _) {}
        });
        printThreadState(thread); // NEW
        thread.start();
        thread.join(1);
        thread.join();
        printThreadState(thread); // TERMINATED
    }

    private static void printThreadState(Thread thread) {
        System.out.println(thread.getName() + ": " + thread.getState());
    }

}
