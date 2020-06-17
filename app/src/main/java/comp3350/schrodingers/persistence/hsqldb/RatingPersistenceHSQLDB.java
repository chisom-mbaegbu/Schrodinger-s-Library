package comp3350.schrodingers.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.schrodingers.objects.Ratings;
import comp3350.schrodingers.persistence.RatingPersistence;

// Class - implements HSQLDB interactions related to book ratings
public class RatingPersistenceHSQLDB implements RatingPersistence {

    // Store the DB path name
    private final String dbPath;

    // Constructor - initialize DB access
    public RatingPersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
    }

    // Method - creates connection to the DB
    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    // Method - read results of a DB query
    private Ratings fromResultSet(final ResultSet rs) throws SQLException {
        final int bookID = rs.getInt("bookID");
        final String email = rs.getString("email");
        final int rate = rs.getInt("rate");
        final String review = rs.getString("review");

        return new Ratings(bookID,email,rate,review);
    }

    // Method - get all ratings
    @Override
    public List<Ratings> getBookRatings(){
        List<Ratings> rateList = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM ratings");
            while (rs.next()) {
                Ratings rates = fromResultSet(rs);
                rateList.add(rates);
            }
            rs.close();
            st.close();

            return rateList;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    // Method - add book rating to DB
    @Override
    public void addBookRatings(int bookid ,int rate, String user, String review){
        List<Ratings> rateList = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO ratings VALUES(?,?,?,?)");

            st.setInt(1,bookid);
            st.setString(2, user);
            st.setInt(3, rate);
            st.setString(4,review);

            st.executeUpdate();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }


}
