package presentation.ui;

import application.services.LibraryService;
import domain.exceptions.BookAlreadyExistsException;
import domain.exceptions.BookNotAvailableException;
import domain.exceptions.BookNotFoundException;
import domain.exceptions.ValidationException;
import domain.models.Book;
import domain.models.LibraryPosition;
import domain.models.PositionStatus;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final String INFO_GREETING = """
            ======================================================
            Приветствуем Вас в консольном приложении "Библиотека"!
            Вы можете взаимодействовать с приложением путем ввода
            номеров команд или пользовательских данных и последую-
            щим нажанием клавиши 'Enter'.
            ======================================================
            """;
    private final String INFO_PROMPT = """
            ================================
            Выберите команду для выполнения:
            0 - Завершить программу
            1 - Добавить книгу в библиотеку
            2 - Удалить книгу из библиотеки
            3 - Найти книгу по названию
            4 - Найти книгу по автору
            5 - Выдать книгу
            6 - Вернуть книгу
            7 - Просмотреть все книги
            ================================
            """;
    private final String ERR_ILLEGAL_COMMAND = """
            >>>>>>>>>>>>|err|<<<<<<<<<<<<<
            Вы ввели некорректную команду.
            Просьба ввести число от 0 до 7
            >>>>>>>>>>>>>>><<<<<<<<<<<<<<<
            """;
    private final String ERR_INVALID_TYPE = """
            >>>>>>>>>>>>>|err|<<<<<<<<<<<<<<
            Вы ввели некорректный тип данных
            Повторите операцию заново
            >>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<
            """;
    private final LibraryService service;
    private final Scanner scanner;

    public ConsoleUI(LibraryService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        printText(INFO_GREETING);
        int command;
        CommandType commandType;
        loop:
        while (true) {
            printText(INFO_PROMPT);
            try {
                command = readCommand();
                commandType = CommandType.fromValue(command);
            } catch (Exception e) {
                printText(ERR_ILLEGAL_COMMAND);
                continue;
            }
            switch (commandType) {
                case EXIT -> {
                    break loop;
                }
                case ADD_BOOK -> handleAddBook();
                case REMOVE_BOOK -> handleRemoveBook();
                case FIND_BOOK_BY_TITLE -> handleFindBookByTitle();
                case FIND_BOOK_BY_AUTHOR -> handleFindBookByAuthor();
                case BORROW_BOOK -> handleBorrowBook();
                case RETURN_BOOK -> handleReturnBook();
                case VIEW_ALL -> handleViewAll();
            }
        }
    }

    private int readCommand() {
        return readInt();
    }

    private String readString() {
        return scanner.nextLine();
    }

    private int readInt() {
        return Integer.parseInt(scanner.nextLine());
    }

    private void printText(String text) {
        System.out.println(text);
    }

    private void handleAddBook() {
        printText("Введите название книги:");
        String title = readString();
        printText("Введите автора:");
        String author = readString();
        printText("Введите ISBN номер книги из 13 цифр:");
        String isbn = readString();
        printText("Введите год издания книги:");

        try {
            int publicationYear = readInt();

            service.addBook(
                    title,
                    author,
                    publicationYear,
                    isbn
            );
            printText("|ok| Книга успешно добавлена!");
        } catch (BookAlreadyExistsException | ValidationException e) {
            printText(e.getMessage());
        } catch (Exception e) {
            printText(ERR_INVALID_TYPE);
        }
    }

    private void handleRemoveBook() {
        printText("Введите ISBN номер книги из 13 цифр:");
        String isbn = readString();

        try {
            service.removeBook(isbn);
            printText("|ok| Книга успешно удалена!");
        } catch (BookNotFoundException e) {
            printText(e.getMessage());
        }
    }

    private void handleFindBookByTitle() {
        printText("Введите заголовок или его начало:");
        String titlePrefix = readString();
        List<LibraryPosition> lps = service.findBooksByTitlePrefix(titlePrefix);
        if (lps.isEmpty()) {
            printText("|ok| В библиотеке нет книг с таким названием");
        } else {
            lps.forEach(this::printLibraryPosition);
        }
    }

    private void handleFindBookByAuthor() {
        printText("Введите автора книг:");
        String author = readString();
        List<LibraryPosition> lps = service.findBooksByAuthor(author);
        if (lps.isEmpty()) {
            printText("|ok| В библиотеке нет книг этого автора");
        } else {
            lps.forEach(this::printLibraryPosition);
        }
    }

    private void handleBorrowBook() {
        printText("Введите ISBN номер книги из 13 цифр:");
        String isbn = readString();
        printText("Введите имя читателя:");
        String borrower = readString();

        try {
            service.borrowBook(isbn, borrower);
            printText("|ok| Книга успешно выдана!");
        } catch (BookNotFoundException | BookNotAvailableException e) {
            printText(e.getMessage());
        }
    }

    private void handleReturnBook() {
        printText("Введите ISBN номер книги из 13 цифр:");
        String isbn = readString();

        try {
            service.returnBook(isbn);
            printText("|ok| Книга успешно возвращена!");
        } catch (BookNotFoundException e) {
            printText(e.getMessage());
        }
    }

    private void handleViewAll() {
        List<LibraryPosition> lps = service.findAllBooks();
        if (lps.isEmpty()) {
            printText("|ok| Библиотека пуста");
        } else {
            lps.forEach(this::printLibraryPosition);
        }
    }

    private void printLibraryPosition(LibraryPosition lp) {
        printText("++++++++++++++++++++++++++++");
        printBookInfo(lp.getBook());
        printText("----------------------------");
        printText(
                lp.getStatus() == PositionStatus.AVAILABLE ?
                        "Статус: Доступна" :
                        String.format("""
                                Статус: Выдана
                                Читатель: %s
                                Дата выдачи: %s
                                """,
                                lp.getBorrower(),
                                lp.getBorrowedAt().toString()
        ));
        printText("++++++++++++++++++++++++++++");
    }

    private void printBookInfo(Book book) {
        printText(String.format("""
                Название: %s
                Автор: %s
                ISBN %s
                Год издания: %d""",
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn().toString(),
                book.getPublicationYear().value()
        ));
    }
}
