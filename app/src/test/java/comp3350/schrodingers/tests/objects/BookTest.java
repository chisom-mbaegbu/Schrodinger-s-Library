package comp3350.schrodingers.tests.objects;

import org.junit.Test;

import comp3350.schrodingers.business.BookBuilder;
import comp3350.schrodingers.objects.Book;
import static org.junit.Assert.*;
public class BookTest {
    @Test
    public void testBook1(){
        Book book;
        System.out.println("\nStarting testBook1");
        BookBuilder builder = new BookBuilder();
        builder.id(1).name("book1").author("author1").price("$100").genre("random").stock("10").icon("icon");
        book = builder.buildBook();
        assertNotNull("it is not null", book);
        assertEquals(1, book.getBookID());
        assertEquals("book1", book.getBookName());
        assertEquals("author1", book.getAuthor());
        assertEquals("$100", book.getPrice());
        assertEquals("random", book.getGenre());
        assertEquals("10", book.getBookStock());
        assertEquals("icon", book.getIconId());

        System.out.println("Finished testBook1");
    }
}
