import java.util.concurrent.TimeUnit;

public class ThreadsInterruptionPractice {

    public static void practice() throws InterruptedException {
        Thread exceptionInterruptedThread = new Thread(() -> {
            throw new RuntimeException();
        });

        exceptionInterruptedThread.start();

        Thread clientThread = new Thread(() -> {
            try {
                while(true) {
                    doRequest();
                }
            } catch (InterruptedException e) {
                System.out.printf(
                        "The thread [%s] was interrupted while sleeping\n",
                        Thread.currentThread().getName()
                );
            }
        });

        clientThread.start();

        Thread obedientThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {}
            System.out.printf(
                    "The thread [%s] obeyed the request to its interruption\n",
                    Thread.currentThread().getName()
            );
        });

        obedientThread.start();

        Thread controlThread = new Thread(() -> {
            if (isServerShouldBeShutDown()) {
                clientThread.interrupt();
                obedientThread.interrupt();
                stopServer();
            }
        });

        TimeUnit.SECONDS.sleep(5);

        controlThread.start();
    }

    private static void doRequest() throws InterruptedException {
        System.out.println("Request was sent");
        TimeUnit.SECONDS.sleep(1);
    }

    private static boolean isServerShouldBeShutDown() {
        return true;
    }

    private static void stopServer() {}
}
