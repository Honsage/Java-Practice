package ru.honsage.practice.config;

import java.nio.file.Path;

public final class AppConfig {
    public static final Path DATA_DIR      = Path.of("data");
    public static final Path INCOMING_DIR  = DATA_DIR.resolve("incoming");
    public static final Path PROCESSED_DIR = DATA_DIR.resolve("processed");

    private AppConfig() {}
}
