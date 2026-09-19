public class ThreadPriorityPractice {

    public static void practice() {
        printThreadPriority(Thread.currentThread()); // 5
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        printThreadPriority(Thread.currentThread()); // 10

        Thread successorThread = new Thread(
                () -> printThreadPriority(Thread.currentThread()) // 10
        );
        successorThread.start();
    }

    private static void printThreadPriority(Thread thread) {
        System.out.println(thread.getName() + ": " + thread.getPriority());
    }
}
