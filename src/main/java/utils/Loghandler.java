package utils;

import datastorage.ConnectionBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

public class Loghandler {

    public void logLogins(String username, boolean loginSuccessful) throws SQLException {
        Connection conn = ConnectionBuilder.getConnection();
        try (Statement st = conn.createStatement()) {
            st.execute(String.format("INSERT INTO LOGS(USERNAME, SUCCESS, DATE, TIME) VALUES ('%s','%s','%s','%s')", username, loginSuccessful, LocalDate.now().toString(), LocalTime.now().toString()));
            conn.commit();
        }
    }
}
