package comp3350.schrodingers.tests.business;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import comp3350.schrodingers.business.BookBuilder;
import comp3350.schrodingers.business.UserBuilder;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.Book;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.persistence.UsersPersistence;
import comp3350.schrodingers.persistence.WishlistPersistence;
import comp3350.schrodingers.business.AccessWishlist;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class AccessWishlistTest {
    private AccessWishlist accessWishlist;
    private WishlistPersistence booksPersistence;
    private UsersPersistence usersPersistence;

    @Before
    public void setup(){
        booksPersistence = mock(WishlistPersistence.class);
        usersPersistence = mock(UsersPersistence.class);
        accessWishlist = new AccessWishlist(usersPersistence,booksPersistence);
    }

    @Test
    public void testGet(){
        System.out.println("\nStarting test AccessWishlist");
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.id(1).name("chris").email("chris@gmail.com");
        User user = userBuilder.buildUser();
        when(usersPersistence.getUser()).thenReturn(user);
        final Book book;
        final List<Book> books = new ArrayList<>();
        BookBuilder builder = new BookBuilder();
        builder.id(21).name("Whirlwind").author("Natalie Hamilton").price("$400").genre("Non-Fiction").stock("30").icon("whirlwind");
        books.add(builder.buildBook());
        when(booksPersistence.getBooks(1)).thenReturn(books);
        try {
            book = accessWishlist.getBooks().get(0);
            assertNotNull("\tbook for default user should not be null", book);
            assertEquals("Book ID should be 21",book.getBookID(), 21);
            verify(booksPersistence).getBooks(1);
            System.out.println("\nFinished test AccessWishlist");
        }catch(UserException u){
            System.out.println("\t"+u.toString());
        }

    }


}
