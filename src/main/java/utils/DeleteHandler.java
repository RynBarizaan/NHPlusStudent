package utils;

import datastorage.DAOFactory;
import datastorage.LockedPatientDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DeleteHandler {
    private final Logger LOGGER = LogManager.getLogger(DeleteHandler.class, StringFormatterMessageFactory.INSTANCE);

    private LockedPatientDAO lockedDao;

    public void checkForCertainLockedTime() throws SQLException {
        lockedDao = DAOFactory.getDAOFactory().createLockedPatientDAO();
        try{
            ResultSet lockedPatientRs = lockedDao.getResultSetFromAll();
            while (lockedPatientRs.next()) {
                LOGGER.info("Ist Datum richtig %s = %s", lockedPatientRs.getString(2), LocalDate.now().toString());
                if (lockedPatientRs.getString(2).equals(LocalDate.now().toString())) {
                    LOGGER.info("Datum ist richtig %s = %s", lockedPatientRs.getString(2), LocalDate.now().toString());
                    deleteTenYearsOldPatient(lockedPatientRs.getInt(1));
                    LOGGER.info("Patient mit PID %s", lockedPatientRs.getInt(1));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
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
