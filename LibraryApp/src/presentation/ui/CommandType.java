package presentation.ui;

public enum CommandType {
    EXIT(0),
    ADD_BOOK(1),
    REMOVE_BOOK(2),
    FIND_BOOK_BY_TITLE(3),
    FIND_BOOK_BY_AUTHOR(4),
    BORROW_BOOK(5),
    RETURN_BOOK(6),
    VIEW_ALL(7);

    private final int value;

    CommandType(int value) {
        this.value = value;
    }

    public static CommandType fromValue(int value) {
        return switch (value) {
            case 0 -> EXIT;
            case 1 -> ADD_BOOK;
            case 2 -> REMOVE_BOOK;
            case 3 -> FIND_BOOK_BY_TITLE;
            case 4 -> FIND_BOOK_BY_AUTHOR;
            case 5 -> BORROW_BOOK;
            case 6 -> RETURN_BOOK;
            case 7 -> VIEW_ALL;
            default ->throw new IllegalArgumentException();
        };
    }
}
