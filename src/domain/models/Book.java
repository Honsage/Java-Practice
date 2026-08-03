package domain.models;

import domain.valueobjects.ISBN;
import domain.valueobjects.PublicationYear;

public class Book {
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
