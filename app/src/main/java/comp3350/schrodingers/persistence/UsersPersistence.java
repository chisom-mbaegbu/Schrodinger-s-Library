package comp3350.schrodingers.persistence;

import comp3350.schrodingers.objects.User;

// Interface - provides skeleton for user persistence
public interface UsersPersistence {
    User insertUser(final User newUser);
    void deleteUser(final String email);
    User findUser(final String email);
    User getUser();
    User editUser(User newUser);
    User getUserAndLogin(String email);
    boolean logout();
}
