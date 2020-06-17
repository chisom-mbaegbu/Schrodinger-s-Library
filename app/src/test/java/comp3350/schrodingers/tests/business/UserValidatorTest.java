package comp3350.schrodingers.tests.business;

import junit.framework.TestCase;

import org.junit.Test;

import comp3350.schrodingers.business.UserBuilder;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.business.UserValidator;
import comp3350.schrodingers.objects.User;
public class UserValidatorTest extends TestCase {
    private UserValidator validator;
    private User user;
    private String correctEmail = "chris@gmail.com";
    private String name = "chris";
    private String password = "comp3350";
    private UserBuilder builder;
    public UserValidatorTest(String arg0){
        super(arg0);
        validator = new UserValidator();
        user = null;
        builder = new UserBuilder();
    }
    private void handleUser(User u){
        try{
            validator.validateInfo(u);
        }catch(UserException e){
            System.out.println("\t"+e);
        }
    }

    @Test(expected= UserException.class)
    public void testNoEmail(){
        System.out.println("\nStarting UserValidatorTest: no email");
        user = builder.id(1).name(name).email("").password(password).buildUser();
        handleUser(user);
        System.out.println("\nFinished UserValidatorTest: no email");
    }
    @Test(expected= UserException.class)
    public void testIncorrectEmail(){
        System.out.println("\nStarting UserValidatorTest: incorrect email");
        user = builder.id(1).name(name).email("holagmail.com").password(password).buildUser();
        handleUser(user);
        System.out.println("\nFinished UserValidatorTest: incorrect email");
    }
    @Test(expected= UserException.class)
    public void testNoName(){
        System.out.println("\nStarting UserValidatorTest: no name");
        user = builder.id(1).name("").email(correctEmail).password(password).buildUser();
        handleUser(user);
        System.out.println("\nFinished UserValidatorTest: no name");
    }
}
