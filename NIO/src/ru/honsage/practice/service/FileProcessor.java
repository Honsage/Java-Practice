package ru.honsage.practice.service;

import ru.honsage.practice.config.AppConfig;
import ru.honsage.practice.model.ProcessingResult;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileProcessor {

    private final HashService hashService = new HashService();

    public ProcessingResult process(Path file, boolean useFileChannel) throws IOException {
        long size = Files.size(file);

        List<String> lines = useFileChannel
                ? readWithFileChannel(file)
                : Files.readAllLines(file, StandardCharsets.UTF_8);

        List<String> dataLines = lines.stream()
                .skip(1)
                .filter(l -> !l.isBlank())
                .toList();

        int records = dataLines.size();

        String sha256 = hashService.sha256(file);

        Path target = AppConfig.PROCESSED_DIR.resolve(file.getFileName());
        Files.move(file, target, StandardCopyOption.REPLACE_EXISTING);

        return new ProcessingResult(
                size,
                sha256,
                records
        );
    }

    private List<String> readWithFileChannel(Path file) throws IOException {
        try (FileChannel channel = FileChannel.open(file, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
            channel.read(buffer);
            buffer.flip();

            String content = StandardCharsets.UTF_8.decode(buffer).toString();
            return content.lines().toList();
        }
    }
}











