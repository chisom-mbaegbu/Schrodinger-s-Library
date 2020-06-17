package comp3350.schrodingers.business;

import comp3350.schrodingers.business.userExceptions.UserAlreadyExistsException;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.persistence.UsersPersistence;

// Class - facilitates accessing user info from DB
public class AccessUserInfo {

    // Store payment access to DB and relevant/current user
    private UsersPersistence userPersistence;

    // Constructor - inject DB access
    public AccessUserInfo(final UsersPersistence userPers) {
        this.userPersistence = userPers;
    }

    // Method - return the user already logged (returns null if not logged in)
    public User getUser() {
        return userPersistence.getUser();
    }

    // Method - insert user into DB
    public User insertUser(User user) throws UserException {
        //add new user
        UserValidator u = new UserValidator();
        u.validateInfo(user);
        User logged = getUser();
        User checkAlreadyAdded = userPersistence.findUser(user.getEmail());
        if(logged == null) {
            if (checkAlreadyAdded != null)
                throw new UserAlreadyExistsException();
            return userPersistence.insertUser(user);
        }
        return updateUser(user);
    }

    // Method - insert user using only basic/necessary info
    public User insertUser(String email, String userName, String password) throws UserException {
        UserBuilder builder = new UserBuilder();
        User newUser = builder.id(0).email(email).name(userName).password(password).buildUser();
        return insertUser(newUser);
    }

    // Method - update user in DB
    public User updateUser(User user) {
        //edit user
        return userPersistence.editUser(user);
    }

    // Method - logout current user
    public boolean logout() {
        return userPersistence.logout();
    }

    // Method - check email and password in order to login
    public User login(String email, String password) {
        User curr = userPersistence.getUserAndLogin(email);
        if (curr != null && curr.getPassword().equals(password)) {
            return curr;
        } else {
            return null;
        }

    }
}
