package domain.valueobjects;

import domain.exceptions.ValidationException;

import java.util.List;

public record ISBN(String value) {
    public ISBN {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ISBN не должен быть пустым!");
        }

        value = value.trim();

        if (!isValidIsbn(value)) {
            throw new ValidationException("Некорректный формат ISBN!");
        }

        value = value.replaceAll("-", "");
    }

    private boolean isValidIsbn(String isbn) {
        boolean isDigit;
        int digitsCount = 0;

        for (char c : isbn.toCharArray()) {
            isDigit = Character.isDigit(c);
            if (!isDigit && c != '-') return false;
            if (isDigit) ++digitsCount;
        }

        return digitsCount == 13;
    }

    @Override
    public String toString() {
        return String.join("-", List.of(
                value.substring(0, 3),
                value.substring(3, 4),
                value.substring(4, 8),
                value.substring(8, 12),
                value.substring(12, 13)
        ));
    }
}
