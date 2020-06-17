package comp3350.schrodingers.persistence;

import java.util.List;

import comp3350.schrodingers.objects.Book;
import comp3350.schrodingers.objects.User;

public interface PurchasedBooksPersistence {
    List<Book> getBooks(int userId);
    boolean insertBook(Book book, int userId);
}
