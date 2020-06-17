package comp3350.schrodingers.tests.business;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.schrodingers.business.AccessBooks;
import comp3350.schrodingers.business.BookBuilder;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.Book;
import comp3350.schrodingers.persistence.BooksPersistence;
import comp3350.schrodingers.persistence.UsersPersistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccessBooksTest {
    private AccessBooks accessBooks;
    private BooksPersistence booksPersistence;

    @Before
    public void setUp(){
       booksPersistence = mock(BooksPersistence.class);
       accessBooks = new AccessBooks(booksPersistence);
    }

    @Test
    public void testGetBook(){
        Book book;
        List<Book> books = new ArrayList<>();
        System.out.println("\nStarting test AccessBooks");
        BookBuilder builder = new BookBuilder();
        builder.id(21).name("Whirlwind").author("Natalie Hamilton").price("$400").genre("Non-Fiction").stock("30").icon("whirlwind");
//        testBook= builder.buildBook();
        books.add(builder.buildBook());
        when(booksPersistence.getAllBooks()).thenReturn(books);
        book =  accessBooks.getAllBooks().get(0);
        assertNotNull("Book should not be null", book);
        assertEquals("Book ID should be 21",book.getBookID(), 21);
        verify(booksPersistence).getAllBooks();
        System.out.println("\nFinished test AccessBooks");

    }
}
