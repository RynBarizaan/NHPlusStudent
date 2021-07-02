package datastorage;

import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAO extends DAOimp<User> {

    public UserDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatementString(User user) {
        return String.format("INSERT INTO USER (username, passwort) VALUES ('%s', '%s')",
                user.getUsername(), user.getPassword());
    }

    @Override
    protected String getReadByIDStatementString(long key) {
        return null;
    }

    @Override
    protected User getInstanceFromResultSet(ResultSet result) throws SQLException {
        User u;
        u = new User(result.getString(1), result.getString(2), result.getLong(3));
        return u;
    }

    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM USER";
    }

    @Override
    protected ArrayList<User> getListFromResultSet(ResultSet set) {
        return null;
    }

    @Override
    protected String getUpdateStatementString(User user) {
        return null;
    }

    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM USER WHERE username = %d", key);
    }

    /**
     * Methode zum finden eines Passworts passend zu einem Usernamen
     *
     * @param username der Username für den das PAsswort gefunden werden soll
     * @return Das Passwort passend zum Usernamen.
     * @throws SQLException wirft eine SQL Exception
     */
    public ResultSet getPasswordFromUsername(String username) throws SQLException {
        Statement st = conn.createStatement();
        return st.executeQuery(String.format("Select PASSWORT FROM USER WHERE username = '%s'", username));
    }
}