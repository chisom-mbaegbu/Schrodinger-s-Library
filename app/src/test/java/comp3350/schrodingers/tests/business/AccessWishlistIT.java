package comp3350.schrodingers.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import comp3350.schrodingers.business.BookBuilder;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.Book;
import comp3350.schrodingers.persistence.UsersPersistence;
import comp3350.schrodingers.persistence.WishlistPersistence;
import comp3350.schrodingers.business.AccessWishlist;
import comp3350.schrodingers.persistence.hsqldb.WishlistPersistenceHSQLDB;
import comp3350.schrodingers.persistence.hsqldb.UsersPersistenceHSQLDB;
import comp3350.schrodingers.tests.utils.TestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class AccessWishlistIT {
    private AccessWishlist accessWishlist;
    private File tempDB;

    @Before
    public void setup() throws IOException{
        this.tempDB = TestUtils.copyDB();
        final WishlistPersistence wishlist = new WishlistPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final UsersPersistence userPers = new UsersPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));

        this.accessWishlist = new AccessWishlist(userPers,wishlist);
    }

    @Test
    public void testGet(){
        System.out.println("\nStarting test AccessWishlist: getBooks");
        try {
            List<Book> books = accessWishlist.getBooks();
            assertNotNull("\tfirst book of default user should not be null", books.get(0));
            System.out.println("\nFinished test AccessWishlist: getBooks");
        }catch(UserException u){
            System.out.println("\t"+u.toString());
        }
    }

    @Test
    public void testInsert(){
        System.out.println("\nStarting AccessWishlist: insertBook");
        BookBuilder builder = new BookBuilder();
        builder.id(1).name("Annabelle Fights Life").author("Jenny Springs").price("$200").genre("Drama").stock("10").icon("annabellefightslife");
        Book book = builder.buildBook();
        try {
            accessWishlist.insertBook(book);
            Book getBook = accessWishlist.getBooks().get(0);
            assertNotNull("\tbook must not be null", getBook);
            assertEquals(getBook.getBookID(), book.getBookID());
        }catch(UserException u){
            System.out.println("\t"+u.toString());
        }
        System.out.println("Finished AccessWishlist: insertBook");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }
}
