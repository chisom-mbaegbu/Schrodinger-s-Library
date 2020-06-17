package comp3350.schrodingers.tests.business;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import comp3350.schrodingers.business.UserBuilder;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.Ratings;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.persistence.RatingPersistence;
import comp3350.schrodingers.business.AccessRatings;
import comp3350.schrodingers.persistence.UsersPersistence;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class AccessRatingsTest {
    private AccessRatings accessRatings;
    private RatingPersistence ratingPersistence;
    private UsersPersistence usersPersistence;

    @Before
    public void setup(){
        ratingPersistence = mock(RatingPersistence.class);
        usersPersistence = mock(UsersPersistence.class);
        accessRatings = new AccessRatings(usersPersistence,ratingPersistence);

    }

    @Test
    public void testFindRatings(){
        System.out.println("\nStarting test AccessRatings");
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.id(1).name("chris").email("chris@gmail.com");
        User user = userBuilder.buildUser();
        when(usersPersistence.getUser()).thenReturn(user);
        List <Ratings> rating = new ArrayList<>();
        Ratings rate = new Ratings(1,"chris@gmail.com",1,"Great Book");
        rating.add(rate);
        when(ratingPersistence.getBookRatings()).thenReturn(rating);
        List <Ratings> ratings = accessRatings.findRatingsByBook(1);
        assertNotNull("\tbook  should not be null", ratings.get(0));
        assertEquals("Book ratings should be 1",ratings.get(0).getRate(),1 );

        System.out.println("\nFinished test AccessRatings");
    }

}
