package ru.honsage.practice.model;

public record ProcessingResult(
        long size,
        String sha256,
        int recordsCount
) {
    @Override
    public String toString() {
        return """
                Size: %d bytes
                SHA-256: %s
                Records: %d
                """.formatted(size, sha256, recordsCount);
    }
}
