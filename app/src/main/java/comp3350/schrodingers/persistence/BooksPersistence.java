package comp3350.schrodingers.persistence;

import java.util.List;

import comp3350.schrodingers.objects.Book;

// Interface - provides skeleton for books persistence
public interface BooksPersistence {
    List<Book> getAllBooks();
}
