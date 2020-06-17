package comp3350.schrodingers.business;

import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.objects.User.Address;
import comp3350.schrodingers.objects.User.Billing;

public class UserBuilder {

    private User tmpUser;

    public UserBuilder() {
        tmpUser = new User();
    }

    // Builder (ie setter) methods
    public UserBuilder id (int userID) {
        tmpUser.setUserId(userID);
        return this;
    }

    public UserBuilder name (String username) {
        tmpUser.setUserName(username);
        return this;
    }

    public UserBuilder email (String email) {
        tmpUser.setEmail(email);
        return this;
    }

    public UserBuilder password (String pass) {
        tmpUser.setPassword(pass);
        return this;
    }

    public UserBuilder billing (Billing billing) {
        tmpUser.setBilling(billing);
        return this;
    }

    public UserBuilder address (Address addr) {
        tmpUser.setAddress(addr);
        return this;
    }

    public User buildUser(){
        return tmpUser;
    }

}
