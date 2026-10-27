package domain.models;

import domain.exceptions.ValidationException;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class LibraryPosition implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Book book;
    private PositionStatus status;
    private String borrower;
    private LocalDate borrowedAt;

    public LibraryPosition(
            Book book,
            PositionStatus status,
            String borrower,
            LocalDate borrowedAt
    ) {
        Objects.requireNonNull(book, "Нельзя создать библиотечную запись с null книгой!");
        Objects.requireNonNull(borrower, "Читатель не должен быть null!");
        Objects.requireNonNull(borrowedAt, "Дата выдачи не должна быть null!");
        if (borrower.isBlank())
            throw new ValidationException("Читатель не может быть пустым!");

        this.book = book;
        this.status = status;
        this.borrower = borrower;
        this.borrowedAt = borrowedAt;
    }

    public Book getBook() {
        return book;
    }
    public PositionStatus getStatus() {
        return status;
    }
    public String getBorrower() {
        return borrower;
    }
    public LocalDate getBorrowedAt() {
        return borrowedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        LibraryPosition other = (LibraryPosition) obj;
        return book.equals(other.book) && status == other.status;
    }

    @Override
    public int hashCode() {
        return book.hashCode() + status.hashCode();
    }
}
