package comp3350.schrodingers.persistence;

import java.util.List;

import comp3350.schrodingers.objects.Ratings;

// Interface - provides skeleton for ratings persistence
public interface RatingPersistence {
    List<Ratings> getBookRatings();
    void addBookRatings(int bookid, int rate, String user, String review);
}
