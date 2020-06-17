package comp3350.schrodingers.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.schrodingers.business.AccessUserInfo;
import comp3350.schrodingers.business.UserBuilder;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.persistence.UsersPersistence;
import comp3350.schrodingers.persistence.hsqldb.UsersPersistenceHSQLDB;
import comp3350.schrodingers.tests.utils.TestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AccessUserInfoIT {
    private AccessUserInfo accessUser;
    private File tempDB;
    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final UsersPersistence userPers = new UsersPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.accessUser = new AccessUserInfo(userPers);
    }

    @Test
    public void testGetUser(){
        System.out.println("\nStarting AccessUserInfoIT: getUser");
        User user = accessUser.getUser();
        assertNotNull("\tfirst user must not be null", user);
        System.out.println("Finished AccessUserInfoIT: getUser");
    }

    @Test
    public void testLogin(){
        System.out.println("\nStarting AccessUserInfoIT: login");
        accessUser.logout();
        accessUser.login("zunenebula@gmail.com", "shield-hero");
        User user = accessUser.getUser();
        assertNotNull("\tuser must not be null", user);
        System.out.println("Finished AccessUserInfoIT: login");
    }

    @Test
    public void testLogout(){
        System.out.println("\nStarting AccessUserInfoIT: logout");
        accessUser.logout();
        User user = accessUser.getUser();
        assertNull("\tuser must be null", user);
        System.out.println("Finished AccessUserInfoIT: logout");
    }

    @Test
    public void testInsertUser(){
        System.out.println("\nStarting AccessUserInfoIT: insertUser");
        UserBuilder builder = new UserBuilder();
        User newUser = builder.id(2).email("chris@gmail.com").name("chris").password("comp3350").buildUser();
        try {
            accessUser.insertUser(newUser);
            User user = accessUser.getUser();
            assertNotNull("\tuser must not be null", user);
            System.out.println("Finished AccessUserInfoIT: insertUser");
        }catch(UserException u){
            System.out.println("\t" + u);
        }
    }

    @Test
    public void testUpdateUser(){
        System.out.println("\nStarting AccessUserInfoIT: updateUser");
        User user = accessUser.getUser();
        UserBuilder builder = new UserBuilder();
        User newUser = builder.id(user.getUserId()).email(user.getEmail()).name(user.getUserName()).password("comp3350").buildUser();
        accessUser.updateUser(newUser);
        assertEquals("\tuser must be equal", newUser, accessUser.getUser());
        assertNotEquals("\tpassword must not be equal", newUser.getPassword(),user.getPassword());
        System.out.println("Finished AccessUserInfoIT: updateUser");
    }


    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }
}
