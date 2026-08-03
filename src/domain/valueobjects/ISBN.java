package domain.valueobjects;

import domain.exceptions.ValidationException;

import java.io.Serializable;
import java.util.List;

public record ISBN(String value) implements Serializable {
    public ISBN {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ISBN не должен быть пустым!");
        }

        value = value.trim();

        if (!isValidIsbn(value)) {
            throw new ValidationException("Некорректный формат ISBN!");
        }

        value = normalize(value);
    }

    private static boolean isValidIsbn(String isbn) {
        boolean isDigit;
        int digitsCount = 0;

        for (char c : isbn.toCharArray()) {
            isDigit = Character.isDigit(c);
            if (!isDigit && c != '-') return false;
            if (isDigit) ++digitsCount;
        }

        return digitsCount == 13;
    }

    private static String normalize(String isbn) {
        return String.join("-", List.of(
                isbn.substring(0, 3),
                isbn.substring(3, 4),
                isbn.substring(4, 8),
                isbn.substring(8, 12),
                isbn.substring(12, 13)
        ));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        ISBN other = (ISBN) obj;
        return this.value.equals(other.value);
    }
}
