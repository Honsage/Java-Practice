package domain.models;

import domain.exceptions.ValidationException;
import domain.valueobjects.ISBN;
import domain.valueobjects.PublicationYear;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String title;
    private String author;
    private ISBN isbn;
    private PublicationYear publicationYear;

    public Book(
            String title,
            String author,
            ISBN isbn,
            PublicationYear publicationYear
    ) {
        Objects.requireNonNull(title, "Заголовок книги не должен быть null!");
        Objects.requireNonNull(author, "Автор книги не может быть null!");
        if (title.isBlank())
            throw new ValidationException("Название книги не может быть пустым!");
        if (author.isBlank())
            throw new ValidationException("Автор книги не может быть пустым!");

        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public ISBN getIsbn() {
        return isbn;
    }
    public PublicationYear getPublicationYear() {
        return publicationYear;
    }
}
