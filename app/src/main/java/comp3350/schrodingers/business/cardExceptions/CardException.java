package comp3350.schrodingers.business.cardExceptions;

// Class - dedicated exception for errors involving ANY credit cards errors
public class CardException extends Exception {

    // Stores the message returned by the exception
    private String message;

    // Constructor - initializes exception message
    public CardException() {
        message = "Card Exception";
    }

    // Method - return the message as a string
    public String toString() {
        return message;
    }
    public void setMessage(String m){
        message = m;
    }

}
