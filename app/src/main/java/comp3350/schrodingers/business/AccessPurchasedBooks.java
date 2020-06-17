package comp3350.schrodingers.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.schrodingers.business.userExceptions.NotLoggedException;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.Book;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.persistence.PurchasedBooksPersistence;
import comp3350.schrodingers.persistence.UsersPersistence;

public class AccessPurchasedBooks {

    private PurchasedBooksPersistence purchasedBooks;
    private AccessUserInfo accessUserInfo;

    // Inject purchased book DB and acquire user access
    public AccessPurchasedBooks(final PurchasedBooksPersistence purBooks){
        this.purchasedBooks = purBooks;
        accessUserInfo = Services.getUserInfoAccess();
    }

    // Inject both purchased book and user DB (testing purposes)
    public AccessPurchasedBooks(final PurchasedBooksPersistence purBooks, final UsersPersistence usersPersistence){
        this.purchasedBooks = purBooks;
        accessUserInfo = new AccessUserInfo(usersPersistence);
    }

    public List<Book> getBooks() throws UserException {
        List<Book> books = new ArrayList<>();
        User user = accessUserInfo.getUser();
        if(user != null)
            books = purchasedBooks.getBooks(user.getUserId());
        else
            throw new NotLoggedException();
        return books;
    }

    public boolean insertBook(Book book) throws UserException{
        User user = accessUserInfo.getUser();
        if(user != null) {
            return purchasedBooks.insertBook(book, user.getUserId());
        }
        throw new NotLoggedException();
    }
}
