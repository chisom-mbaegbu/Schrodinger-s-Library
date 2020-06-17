package comp3350.schrodingers.tests.business;

import org.junit.Before;
import org.junit.Test;

import comp3350.schrodingers.business.AccessUserInfo;
import comp3350.schrodingers.business.UserBuilder;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.persistence.UsersPersistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccessUserInfoTest {
    private AccessUserInfo accessUserInfo;
    private UsersPersistence usersPersistence;
    private UserBuilder builder;

    @Before
    public void setUp(){
        usersPersistence = mock(UsersPersistence.class);
        accessUserInfo = new AccessUserInfo(usersPersistence);
        builder = new UserBuilder();
    }

    @Test
    public void testGetUser(){
        System.out.println("\nStarting test AccessUserInfo");
        final User user = builder.id(1).name("Zune").email("zunenebula@gmail.com").password("shield-hero").buildUser();
        when(usersPersistence.getUser()).thenReturn(user);
        final User getUser = accessUserInfo.getUser();
        assertNotNull("\tfirst logged user should not be null", getUser);
        assertEquals("Zune", getUser.getUserName());
        verify(usersPersistence).getUser();
        System.out.println("\nFinished test AccessUserInfo");
    }

}
