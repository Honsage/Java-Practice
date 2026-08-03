package domain.valueobjects;

import domain.exceptions.ValidationException;

import java.time.LocalDateTime;

public record PublicationYear(int value) {
    public PublicationYear {
        if (!isValidPublicationYear(value)) {
            throw new ValidationException("Некорректный год публикации!");
        }
    }

    private boolean isValidPublicationYear(int year) {
        // TODO: Уточнить в предметной области валидные года публикации
        return year >= 1600 && year <= LocalDateTime.now().getYear();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
