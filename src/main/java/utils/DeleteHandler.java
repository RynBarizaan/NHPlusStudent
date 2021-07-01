package utils;

import datastorage.DAOFactory;
import datastorage.LockedPatientDAO;
import model.LockedPatient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DeleteHandler {

    private LockedPatientDAO lockedDao;

    public void checkForCertainTime() throws SQLException {
        lockedDao = DAOFactory.getDAOFactory().createLockedPatientDAO();
        try (ResultSet lockedPatientRs = lockedDao.getResultSetFromAll()) {
            while (lockedPatientRs.next()) {
                if (lockedPatientRs.getString("TODELETEDATE").toString() == LocalDate.now().toString()) {
                    deleteTenYearsOldPatient(lockedPatientRs.getLong("PID"));
                }
            }
        }
    }

    /**
     *
     * @param PID
     * @throws SQLException
     */
    private void deleteTenYearsOldPatient(long PID) throws SQLException {
        lockedDao = DAOFactory.getDAOFactory().createLockedPatientDAO();
        lockedDao.deleteById(PID);

        //TODO: Löschen nach bestimmter zeit funktioniert nicht.
    }
}
