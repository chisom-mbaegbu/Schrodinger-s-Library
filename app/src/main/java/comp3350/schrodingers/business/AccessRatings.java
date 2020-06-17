package comp3350.schrodingers.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import comp3350.schrodingers.business.userExceptions.NotLoggedException;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.Ratings;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.persistence.RatingPersistence;
import comp3350.schrodingers.persistence.UsersPersistence;
public class AccessRatings {

    private AccessUserInfo accessUserInfo;
    private RatingPersistence ratingPersistence;

    // Constructor - inject DB access
    public AccessRatings(RatingPersistence ratePersistence){
        accessUserInfo = Services.getUserInfoAccess();
        ratingPersistence = ratePersistence;
    }
    // Constructor for testing
    public AccessRatings(UsersPersistence usersPersistence, final RatingPersistence ratingPersistence) {
        this.ratingPersistence = ratingPersistence;
        accessUserInfo = new AccessUserInfo(usersPersistence);
    }

    public List<Ratings> findRatingsByBook(int bookID){
        List<Ratings> ratings = ratingPersistence.getBookRatings();
        List<Ratings> bookRatings = new ArrayList<>();
        Iterator<Ratings> rateIterator = ratings.iterator();
        while (rateIterator.hasNext()) {
            Ratings nextRate = rateIterator.next();  //holds the next rating found in the list
            if (nextRate.getBookID() == bookID ) {
                bookRatings.add(nextRate);
            }

        }//returns a books by the author.
        return bookRatings;
    }

    public void addRating(int bookid, int rate, String review) throws UserException {
        User user = accessUserInfo.getUser();
        if(user != null)
            ratingPersistence.addBookRatings(bookid, rate, user.getEmail(),review);
        else
            throw new NotLoggedException();
    }

    public Ratings getRatingsByUser(int bookID, String email){

        List<Ratings> bookRatings = findRatingsByBook(bookID);
        Ratings userRating =  new Ratings(-1,null,-1, null);

        Iterator<Ratings> ratingsIterator = bookRatings.iterator();

        while (ratingsIterator.hasNext()){
            Ratings nexRate  = ratingsIterator.next();
            if(nexRate.getEmail().equalsIgnoreCase(email)){
                userRating = nexRate;
            }
        }

        return userRating;
    }
}
