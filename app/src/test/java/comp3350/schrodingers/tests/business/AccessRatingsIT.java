package comp3350.schrodingers.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.schrodingers.business.AccessRatings;
import comp3350.schrodingers.business.userExceptions.NotLoggedException;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.Ratings;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.persistence.UsersPersistence;
import comp3350.schrodingers.persistence.hsqldb.UsersPersistenceHSQLDB;
import comp3350.schrodingers.persistence.RatingPersistence;
import comp3350.schrodingers.persistence.hsqldb.RatingPersistenceHSQLDB;
import comp3350.schrodingers.tests.utils.TestUtils;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class AccessRatingsIT {
    private AccessRatings accessRatings;
    private File tempDB;

    @Before
    public void setup() throws IOException{
        this.tempDB = TestUtils.copyDB();
        final RatingPersistence ratings = new RatingPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final UsersPersistence userPers = new UsersPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));

        this.accessRatings = new AccessRatings(ratings);
    }

    @Test
    public void testFindRatingsByBook(){
        // List<Ratings> findRatingsByBook(int bookID)
        System.out.println("\nStarting test AccessRatings: findRatingsByBook");
        try {
            accessRatings.addRating(1,2,"Great Book");
            List <Ratings> rate= accessRatings.findRatingsByBook(1);
            assertNotNull("\tfirst book of default user should not be null", rate.get(0));
            assertEquals(rate.get(0).getRate(),2);
        } catch (UserException e) {
            e.printStackTrace();
        }
        System.out.println("Finished test AccessRatings: findRatingsByBook" );
    }

    @Test
    public void testAddRating(){
        //void addRating(int bookid, int rate, String review)
        System.out.println("\nStarting test AccessRatings: addRating");
        try {
            accessRatings.addRating(1,2,"Great Book");
            List <Ratings> rate= accessRatings.findRatingsByBook(1);
            assertNotNull("\tRatings should not be null", rate.get(0));
            assertEquals(rate.get(0).getRate(),2);
        } catch (UserException e) {
            e.printStackTrace();
        }
        System.out.println("Finished test AccessRatings: addRating" );
    }

    @Test
    public void testgetRatingsByUser(){
        //Ratings getRatingsByUser(int bookID, String email)
        System.out.println("\nStarting test AccessRatings: getRatingsByUser");
        try {
            accessRatings.addRating(1,2,"Great Book");
            Ratings rate= accessRatings.getRatingsByUser(1,"zunenebula@gmail.com");
            assertNotNull("\tRatings should not be null", rate);
            assertEquals(rate.getEmail(), "zunenebula@gmail.com");
        } catch (UserException e) {
            e.printStackTrace();
        }

        System.out.println("Finished test AccessRatings: getRatingsByUser" );
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }
}
