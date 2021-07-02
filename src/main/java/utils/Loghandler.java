package utils;

import datastorage.ConnectionBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Klasse zum händeln der speicherung der LoginLogs
 */
public class Loghandler {

    /**
    * Wenn ein Loginversuch stattfindet werden Daten wie Benutzername, ob der Login erfolgreich war und zu welchem
     * zeitpunkt der versuch stattgefunden hat in einer seperaten Datenbank gespeichert.
     */
    public void logLogins(String username, boolean loginSuccessful) throws SQLException {
        Connection conn = ConnectionBuilder.getConnection();
        try (Statement st = conn.createStatement()) {
            st.execute(String.format("INSERT INTO LOGS(USERNAME, SUCCESS, DATE, TIME) VALUES ('%s','%s','%s','%s')", username, loginSuccessful, LocalDate.now().toString(), LocalTime.now().toString()));
            conn.commit();
        }
    }
}
