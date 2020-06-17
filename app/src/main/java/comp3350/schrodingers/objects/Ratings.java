package comp3350.schrodingers.objects;

public class Ratings {

    private int bookID;
    private String email;
    private int rate;
    private String review;

    public Ratings( int bookID, String email, int rate,String review) {

        this.bookID = bookID;
        this.email = email;
        this.rate = rate;
        this.review = review;
    }



    public int getBookID() {
        return bookID;
    }

    public String getEmail() {
        return email;
    }

    public int getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "Rated " + this.rate + " By " + this.email + "\n"+ this.review;
    }
}
