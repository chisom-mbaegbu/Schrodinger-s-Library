package comp3350.schrodingers.business.userExceptions;

// Class - dedicated exception for errors involving users
public class UserException extends Exception{

    // Stores the message returned by the exception
    private String message;

    // Constructor - initializes exception message
    public UserException() {
        message = "User Exception";
    }

    // Method - return the message as a string
    public String toString() {
        return message;
    }

    public void setMessage(String m){
        message = m;
    }
}
