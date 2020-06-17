package comp3350.schrodingers.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comp3350.schrodingers.business.AccessBooks;
import comp3350.schrodingers.objects.Book;
import comp3350.schrodingers.persistence.BooksPersistence;
import comp3350.schrodingers.persistence.hsqldb.BooksPersistenceHSQLDB;
import comp3350.schrodingers.tests.utils.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class AccessBooksIT {
    private AccessBooks accessBooks;
    private File tempDB;

    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final BooksPersistence booksPersistence = new BooksPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        accessBooks = new AccessBooks(booksPersistence);
    }

    @Test
    public void testGetAllBooks(){
        // getAllBooks()
        System.out.println("\nStarting AccessBooks: getAllBooks");
        List <Book> books =accessBooks.getAllBooks();
        assertNotNull("\tBooks should not be null", books);
        System.out.println("Finished AccessBooks: getAllBooks");

    }

    @Test
    public void testSearchBookById(){

        System.out.println("\nStarting AccessBooks: searchBookById");
        Book book =accessBooks.searchBookById(1);
        assertNotNull("\tShould not be null", book);
        assertEquals("\tThe book title is not correct","Annabelle Fights Life",book.getBookName());

        System.out.println("Finished AccessBooks: searchBookById");

    }


    @Test
    public void testSearchBookByAuthor(){
        System.out.println("\nStarting AccessBooks: searchBookByAuthor");
        List <Book> books = accessBooks.searchBookByAuthor("Jenny Springs");
        assertNotNull("\tShould not be null", books);
        System.out.println("Finished AccessBooks: searchBookByAuthor");
    }

    @Test
    public void testSearchBookByTitle(){
        //List<Book> searchBookByTitle(String title)
        System.out.println("\nStarting AccessBooks: searchBookByTitle");
        List <Book> books =accessBooks.searchBookByTitle("Annabelle Fights Life");
        assertNotNull("\tShould not be null", books);

        System.out.println("Finished AccessBooks: searchBookByTitle");
    }

    @Test
    public void testSearchBookByGenre(){
        // List<Book> searchBookByGenre(String genre, int count)
        System.out.println("\nStarting AccessBooks: searchBookByGenre");
        List <Book> books =accessBooks.searchBookByGenre("Drama", 2);
        assertNotNull("\tShould not be null", books);

        System.out.println("Finished AccessBooks: searchBookByGenre");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }

}
