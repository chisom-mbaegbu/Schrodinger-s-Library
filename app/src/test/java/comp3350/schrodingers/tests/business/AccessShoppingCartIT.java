package comp3350.schrodingers.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.schrodingers.business.AccessShoppingCart;
import comp3350.schrodingers.business.BookBuilder;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.Book;
import comp3350.schrodingers.persistence.ShoppingCartPersistence;
import comp3350.schrodingers.persistence.UsersPersistence;
import comp3350.schrodingers.persistence.hsqldb.ShoppingCartPersistenceHSQLDB;
import comp3350.schrodingers.persistence.hsqldb.UsersPersistenceHSQLDB;
import comp3350.schrodingers.tests.utils.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccessShoppingCartIT {

    private AccessShoppingCart shoppingCartAccess;
    private File tempDB;

    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final ShoppingCartPersistence shoppingCartAccess = new ShoppingCartPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final UsersPersistence userPers = new UsersPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));

        this.shoppingCartAccess = new AccessShoppingCart(userPers, shoppingCartAccess);
    }

    @Test
    public void testInsert(){
        System.out.println("\nStarting AccessShoppingCart: insertBook");
        BookBuilder builder = new BookBuilder();
        builder.id(1).name("Annabelle Fights Life").author("Jenny Springs").price("$200").genre("Drama").stock("10").icon("annabellefightslife");
        Book book = builder.buildBook();
        try {
            shoppingCartAccess.insertBook(book);
            Book getBook = shoppingCartAccess.getBooks().get(0);
            assertNotNull("\tbook must not be null", getBook);
            assertEquals(getBook.getBookID(), book.getBookID());
        }catch(UserException u){
            System.out.println("\t"+u.toString());
        }
        System.out.println("Finished AccessShoppingCart: insertBook");
    }

    @Test
    public void testGet(){
        System.out.println("\nStarting test AccessShoppingCart: getBooks");

        BookBuilder builder = new BookBuilder();
        builder.id(1).name("Annabelle Fights Life").author("Jenny Springs").price("$200").genre("Drama").stock("10").icon("annabellefightslife");
        Book book = builder.buildBook();

        try {
            shoppingCartAccess.insertBook(book);
            List<Book> books = shoppingCartAccess.getBooks();
            assertNotNull("\tfirst book of default user should not be null", books.get(0));
            System.out.println("\nFinished test AccessPurchasedBooks: getBooks");
        }catch(UserException u){
            System.out.println("\t"+u.toString());
        }
    }

    @Test
    public void testEmpty(){
        System.out.println("\nStarting AccessShoppingCart: emptyCart");
        BookBuilder builder = new BookBuilder();
        builder.id(1).name("Annabelle Fights Life").author("Jenny Springs").price("$200").genre("Drama").stock("10").icon("annabellefightslife");
        Book book = builder.buildBook();
        try {
            shoppingCartAccess.insertBook(book);
            Book getBook = shoppingCartAccess.getBooks().get(0);
            assertNotNull("\tbook must not be null", getBook);
            assertEquals(getBook.getBookID(), book.getBookID());

            shoppingCartAccess.emptyCart();
            assertEquals("\tshopping cart is not empty when it should be", shoppingCartAccess.getBooks().size(), 0);

        }catch(UserException u){
            System.out.println("\t"+u.toString());
        }
        System.out.println("Finished AccessShoppingCart: insertBook");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }
}
