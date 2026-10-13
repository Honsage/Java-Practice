package ru.honsage.practice.service;

import ru.honsage.practice.model.ProcessingResult;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

public class DirectoryWatcher {

    private final FileProcessor processor;

    public DirectoryWatcher(FileProcessor processor) {
        this.processor = processor;
    }

    public void watch(Path dir) throws IOException, InterruptedException {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            System.out.println("Наблюдение за каталогом: " + dir.toAbsolutePath() + "\n");

            while (true) {
                WatchKey key = watchService.poll(1, TimeUnit.SECONDS);
                if (key == null) continue;

                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        Path fileName = (Path) event.context();
                        Path fullPath = dir.resolve(fileName);

                        if (!fileName.toString().toLowerCase().endsWith(".csv")) continue;

                        System.out.println("Обнаружен новый файл: " + fileName);

                        waitUntilStable(fullPath);

                        try {
                            ProcessingResult result = processor.process(fullPath, false);
                            System.out.println(result);
                        } catch (IOException e) {
                            System.err.println("Ошибка обработки " + fileName + ": " + e.getMessage());
                        }
                    }
                }
                if (!key.reset()) break;
            }
        }
    }

    private void waitUntilStable(Path file) throws InterruptedException, IOException {
        long prev = -1;
        for (int i = 0; i < 20; i++) {
            if (Files.notExists(file)) {
                Thread.sleep(100);
                continue;
            }
            long size = Files.size(file);
            if (size == prev && size > 0) return;
            prev = size;
            Thread.sleep(100);
        }
    }
}
