import java.util.Arrays;
import java.util.Random;

public class JoinPractice {

    private static final long STREAM_SIZE = 1000;
    private static final int INT_ORIGIN = 0;
    private static final int INT_BOUND = 10;

    private static final int FIRST_THREAD_LEFT_BOUND = 0;
    private static final int FIRST_THREAD_RIGHT_BOUND = 500;

    private static final int SECOND_THREAD_LEFT_BOUND = 500;
    private static final int SECOND_THREAD_RIGHT_BOUND = 1000;

    public static void practice() throws InterruptedException {
        int[] array = new Random().ints(STREAM_SIZE, INT_ORIGIN, INT_BOUND).toArray();

        SubarraySummationTask firstTask = new SubarraySummationTask(FIRST_THREAD_LEFT_BOUND, FIRST_THREAD_RIGHT_BOUND, array);
        Thread firstThread = new Thread(firstTask);
        firstThread.start();

        SubarraySummationTask secondTask = new SubarraySummationTask(SECOND_THREAD_LEFT_BOUND, SECOND_THREAD_RIGHT_BOUND, array);
        Thread secondThread = new Thread(secondTask);
        secondThread.start();

        waitForTasksFinished(firstThread, secondThread);

        int resultSum = firstTask.getResult() + secondTask.getResult();
        printThreadNameAndResult(resultSum);
    }

    private static void waitForTasksFinished(Thread... threads) throws InterruptedException {
        for (var thread : threads)
            thread.join();
    }

    private static void printThreadNameAndResult(int result) {
        System.out.printf("%s : %d\n", Thread.currentThread().getName(), result);
    }

    private static final class SubarraySummationTask implements Runnable {

        private static final int INITIAL_RESULT_VALUE = 0;

        private final int leftIndex;
        private final int rightIndex;

        private final int[] array;

        private int result;

        public SubarraySummationTask(int leftIndex, int rightIndex, int[] array) {
            this.leftIndex = leftIndex;
            this.rightIndex = rightIndex;
            this.array = array;
            this.result = INITIAL_RESULT_VALUE;
        }

        @Override
        public void run() {
            Arrays.stream(array, leftIndex, rightIndex).forEach(i -> result += i);
            printThreadNameAndResult(result);
        }

        public int getResult() {
            return result;
        }
    }
}
