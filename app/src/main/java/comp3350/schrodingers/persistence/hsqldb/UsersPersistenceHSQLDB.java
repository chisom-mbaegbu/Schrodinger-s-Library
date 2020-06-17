package comp3350.schrodingers.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.business.AccessPaymentInfo;
import comp3350.schrodingers.business.UserBuilder;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.persistence.UsersPersistence;

public class UsersPersistenceHSQLDB implements UsersPersistence {

    private final String dbPath;

    private User logged;
    private int userId = 1;

    public UsersPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
        logged = findLoggedUser();

    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private User fromResultSet(final ResultSet rs) throws SQLException {
        final int id = rs.getInt("userId");
        final String email = rs.getString("email");
        final String username = rs.getString("name");
        final String password = rs.getString("password");
        final long cardNum = rs.getLong("cardNum");
        final String address = rs.getString("numAndStreet");

        UserBuilder builder = new UserBuilder();
        if (cardNum == 0 && address.compareTo("") == 0)
            return builder.id(id).name(username).email(email).password(password).buildUser();
        else {
            AccessPaymentInfo payInfo = Services.getPaymentInfoAccess();
            User.Billing card = payInfo.getUserCard(email);
            User.Address add = findAddress(address);
            return builder.id(id).name(username).email(email).password(password).billing(card).address(add).buildUser();
        }
    }

    public User insertUser(final User newUser) {
        try (final Connection c = connection()) {
            final Statement up = c.createStatement();
            up.executeQuery("UPDATE user SET LOGGED = FALSE");
            userId++;
            final PreparedStatement st = c.prepareStatement("INSERT INTO user VALUES(?, ?, ?, ?, ?, ?, ?)");
            st.setInt(1, userId);
            st.setString(2, newUser.getEmail());
            st.setString(3, newUser.getUserName());
            st.setString(4, newUser.getPassword());
            st.setBoolean(5, true);
            st.setLong(6, newUser.getBilling().getCardNumber());

            if (newUser.getAddress().getAddress().compareTo("") != 0)
                st.setString(7, newUser.getAddress().getAddress());
            else
                st.setString(7, "NOADDRESS!");

            st.executeUpdate();

            logged = newUser;
            return logged;

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public void deleteUser(final String email) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM user WHERE email = ?");
            st.setString(1, email);
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public User getUser() {
        return logged;
    }

    public User editUser(User newUser) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE user SET email = ?, name = ?, password = ?, logged = TRUE, cardNum = ?, numAndStreet = ? WHERE userId = ?");
            st.setString(1, newUser.getEmail());
            st.setString(2, newUser.getUserName());
            st.setString(3, newUser.getPassword());

            st.setLong(4, newUser.getBilling().getCardNumber());

            String loggedAddr = logged.getAddress().getAddress();
            String newUserAddr = newUser.getAddress().getAddress();

            if(newUserAddr.compareTo("") == 0 || newUser.getAddress().noAddr())
                st.setString(5, "NOADDRESS!");
            else if (loggedAddr.compareTo(newUserAddr) == 0 && !findAddress(newUserAddr).noAddr()) {
                st.setString(5, newUserAddr);
            } else{
                insertAddress(newUser.getAddress());
                st.setString(5, newUserAddr);
            }

            st.setInt(6, logged.getUserId());

            st.executeUpdate();

            logged = newUser;
            return logged;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public User findLoggedUser() {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM user WHERE logged = TRUE");

            final ResultSet rs = st.executeQuery();

            final User user;
            if (rs.next())
                user = fromResultSet(rs);
            else
                user = null;

            rs.close();
            st.close();

            return user;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public User findUser(final String email) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM user WHERE email = ?");
            st.setString(1, email);

            final ResultSet rs = st.executeQuery();

            final User user;
            if (rs.next())
                user = fromResultSet(rs);
            else
                user = null;

            rs.close();
            st.close();

            return user;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private User.Address insertAddress(final User.Address address) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO address VALUES(?, ?, ?, ?, ?)");
            st.setString(1, address.getAddress());
            st.setString(2, address.getPostalCode());
            st.setString(3, address.getCity());
            st.setString(4, address.getState());
            st.setString(5, address.getCountry());

            st.executeUpdate();

            return address;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private User.Address findAddress(final String house) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM address WHERE numAndStreet = ?");
            st.setString(1, house);

            final ResultSet rs = st.executeQuery();

            final User.Address address;
            if (rs.next())
                address = fromResultAddress(rs);
            else
                address = new User.Address();

            rs.close();
            st.close();

            return address;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private User.Address fromResultAddress(final ResultSet rs) throws SQLException {
        final String house = rs.getString("numAndStreet");
        final String zip = rs.getString("PC");
        final String city = rs.getString("city");
        final String state = rs.getString("state");
        final String country = rs.getString("country");

        return new User.Address(house, zip, city, state, country);
    }


    public User getUserAndLogin(String email) {
        logged = findUser(email);
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE user SET LOGGED = TRUE WHERE email=?");
            st.setString(1, logged.getEmail());
            st.executeUpdate();
            return logged;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public boolean logout() {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE user SET LOGGED = FALSE WHERE email=?");
            st.setString(1, logged.getEmail());
            st.executeUpdate();
            logged = null;
            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
