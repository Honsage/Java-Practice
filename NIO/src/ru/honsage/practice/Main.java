package ru.honsage.practice;

import ru.honsage.practice.config.AppConfig;
import ru.honsage.practice.model.ProcessingResult;
import ru.honsage.practice.service.DirectoryService;
import ru.honsage.practice.service.DirectoryWatcher;
import ru.honsage.practice.service.FileProcessor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        DirectoryService dirService = new DirectoryService();
        FileProcessor processor = new FileProcessor();

        dirService.ensureDirectory(AppConfig.DATA_DIR);
        dirService.ensureDirectory(AppConfig.INCOMING_DIR);
        dirService.ensureDirectory(AppConfig.PROCESSED_DIR);

        dirService.printDirectoryInfo(AppConfig.INCOMING_DIR);

        List<Path> csvFiles = dirService.findFilesByExtension(AppConfig.INCOMING_DIR, "csv");
        System.out.println("Найдено CSV-файлов: " + csvFiles.size() + "\n");

        boolean firstFile = true;
        for (Path file : csvFiles) {
            System.out.println("Обработка файла: " + file.getFileName());
            ProcessingResult result = processor.process(file, firstFile);
            firstFile = false;
            System.out.println(result);
        }

        DirectoryWatcher watcher = new DirectoryWatcher(processor);
        watcher.watch(AppConfig.INCOMING_DIR);
    }
}
