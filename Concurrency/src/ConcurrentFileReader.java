import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentFileReader {
    private static final int CORES_COUNT;
    private final ExecutorService executorService;
    private long chunkSize;

    static {
        CORES_COUNT = Runtime.getRuntime().availableProcessors();
    }

    public ConcurrentFileReader() {
        this(1 << 20);
    }

    public ConcurrentFileReader(long chunkSize) {
        this.chunkSize = chunkSize;
        this.executorService = Executors.newFixedThreadPool(CORES_COUNT);
    }

    public String read(String pathname) {
        StringBuffer content = new StringBuffer();
        try (RandomAccessFile file = new RandomAccessFile(pathname, "r");
             FileChannel channel = file.getChannel()) {

            long fileSize = channel.size();
            CountDownLatch latch = new CountDownLatch((int)(fileSize/chunkSize + 1));

            for (long i = 0; i < fileSize; i += chunkSize) {
                final long start = i;
                final long end = Math.min(i + chunkSize, fileSize);
                this.executorService.submit(() -> {
                    try {
                        MappedByteBuffer buffer = channel.map(
                                FileChannel.MapMode.READ_ONLY, start, end - start
                        );
                        byte[] data = new byte[(int)(end - start)];
                        buffer.get(data);

                        synchronized (content) {
                            content.append(new String(data, StandardCharsets.UTF_8));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.executorService.shutdown();
        }
        return content.toString();
    }

    public void setChunkSize(long chunkSize) {
        if (chunkSize > 0)
            this.chunkSize = chunkSize;
    }
}
