package domain.valueobjects;

import domain.exceptions.ValidationException;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public record PublicationYear(int value) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public PublicationYear {
        if (!isValidPublicationYear(value)) {
            throw new ValidationException("Некорректный год публикации!");
        }
    }

    private boolean isValidPublicationYear(int year) {
        return year >= 0 && year <= LocalDateTime.now().getYear();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
