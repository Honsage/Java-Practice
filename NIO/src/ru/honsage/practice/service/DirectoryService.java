package ru.honsage.practice.service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class DirectoryService {

    public void ensureDirectory(Path dir) throws IOException {
        if (Files.notExists(dir)) {
            Files.createDirectories(dir);
            System.out.println("Создан каталог: " + dir.toAbsolutePath());
        } else {
            System.out.println("Каталог уже существует: " + dir.toAbsolutePath());
        }
    }

    public void printDirectoryInfo(Path dir) throws IOException {
        System.out.println("\nСодержимое каталога: " + dir + "");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                BasicFileAttributes attrs = Files.readAttributes(
                        file, BasicFileAttributes.class);

                System.out.printf(
                        "%s | %d bytes | %s%n",
                        file.getFileName(),
                        attrs.size(),
                        attrs.lastModifiedTime()
                );
            }
        }
        System.out.println();
    }

    public List<Path> findFilesByExtension(Path dir, String ext) throws IOException {
        List<Path> result = new ArrayList<>();
        String mask = "*." + ext;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, mask)) {
            for (Path file : stream) {
                result.add(file);
            }
        }
        return result;
    }
}
