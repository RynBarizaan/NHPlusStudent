package datastorage;

import model.LockedPatient;
import model.Patient;
import utils.DateConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class LockedPatientDAO extends DAOimp<LockedPatient> {

    /**
     * constructs Object. Calls the Constructor from <code>DAOImp</code> to store the connection.
     * @param conn
     */
    public LockedPatientDAO(Connection conn) { super(conn); }

    /**
     * generates a <code>INSERT INTO</code>-Statement for a given locked patient
     * @param lockedpatient for which a specific INSERT INTO is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getCreateStatementString(LockedPatient lockedpatient) {
        return String.format("INSERT INTO lockedpatient (firstname, surname, dateOfBirth, carelevel, roomnumber, toDeleteDate) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                lockedpatient.getFirstName(), lockedpatient.getSurname(), lockedpatient.getDateOfBirth(), lockedpatient.getCareLevel(), lockedpatient.getRoomnumber(), lockedpatient.getToDeleteDate());
    }

    /**
     * generates a <code>select</code>-Statement for a given key
     * @param key for which a specific SELECT is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM lockedpatient WHERE pid = %d", key);
    }

    /**
     * maps a <code>ResultSet</code> to a <code>LockedPatient</code>
     * @param result ResultSet with a single row. Columns will be mapped to a lockedpatient-object.
     * @return locked patient with the data from the resultSet.
     */
    @Override
    protected LockedPatient getInstanceFromResultSet(ResultSet result) throws SQLException {
        LockedPatient p = null;
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
        LocalDate endDate = DateConverter.convertStringToLocalDate(result.getString(6));
        p = new LockedPatient(result.getInt(1), result.getString(2),
                result.getString(3), date, result.getString(5),
                result.getString(6), endDate);
        return p;
    }

    /**
     * generates a <code>SELECT</code>-Statement for all locked patients.
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM lockedpatient";
    }

    /**
     * maps a <code>ResultSet</code> to a <code>LockedPatient-List</code>
     * @param result ResultSet with a multiple rows. Data will be mapped to lockedpatient-object.
     * @return ArrayList with locked patients from the resultSet.
     */
    @Override
    protected ArrayList<LockedPatient> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<LockedPatient> list = new ArrayList<>();
        LockedPatient p = null;
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
            LocalDate endDate = DateConverter.convertStringToLocalDate(result.getString(6));
            p = new LockedPatient(result.getInt(1), result.getString(2),
                    result.getString(3), date, result.getString(5),
                    result.getString(6), endDate);
            list.add(p);
        }
        return list;
    }

    /**
     * generates a <code>UPDATE</code>-Statement for a given locked patient
     * @param lockedpatient for which a specific update is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getUpdateStatementString(LockedPatient lockedpatient) {
        return String.format("UPDATE lockedpatient SET firstname = '%s', surname = '%s', dateOfBirth = '%s', carelevel = '%s', " +
                "roomnumber = '%s', toDeleteDate = '%s' WHERE pid = %d", lockedpatient.getFirstName(), lockedpatient.getSurname(),
                lockedpatient.getDateOfBirth(), lockedpatient.getCareLevel(), lockedpatient.getRoomnumber(), lockedpatient.getToDeleteDate(),
                lockedpatient.getPid());
    }

    /**
     * generates a <code>delete</code>-Statement for a given key
     * @param key for which a specific DELETE is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM lockedpatient WHERE pid=%d", key);
    }

}
