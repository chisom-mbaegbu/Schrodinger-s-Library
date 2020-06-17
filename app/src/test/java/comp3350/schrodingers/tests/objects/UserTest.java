package comp3350.schrodingers.tests.objects;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

import comp3350.schrodingers.business.UserBuilder;
import comp3350.schrodingers.objects.User;

// tests the user class's constructor and main fields
//to do later - add address tests
public class UserTest {

    @Test
    public void builderIsCorrect()
    {
        UserBuilder builder = new UserBuilder();
        User testUser = builder.id(1).name("michael").email("axa@gmail.com").password("voo").buildUser();
        System.out.println("\nStarting user test\n");
        assertEquals(testUser.getEmail(), "axa@gmail.com");
        assertEquals(testUser.getUserName(), "michael");
        assertEquals(testUser.getPassword(), "voo");
        System.out.println("\nFinished user test\n");

    }

}
