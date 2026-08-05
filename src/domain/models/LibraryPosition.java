package domain.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

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
}
