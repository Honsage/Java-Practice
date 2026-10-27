import presentation.ui.ConsoleUI;

public class LibraryApp {
    public static void main(String[] args) {
        LibraryContext context = new LibraryContext();

        ConsoleUI ui = new ConsoleUI(context.getService());
        ui.start();

        context.saveData();
    }
}