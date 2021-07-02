package utils;

import datastorage.DAOFactory;
import datastorage.LockedPatientDAO;
import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Klasse zum löschen von Patienten und Treatments
 */
public class DeleteHandler {
    private final Logger LOGGER = LogManager.getLogger(DeleteHandler.class, StringFormatterMessageFactory.INSTANCE);

    /**
     * prüft ob ein Patient schon länger als 10 Jahre im Lock ist und löscht diesen Patienten und dessen
     * dazugehörigen Treatments falls sich Patient länger als 10 Jahre im Lock befindet
     * @throws SQLException
     */
    public void deleteLockedPatientAfter10Years() throws SQLException {
        LockedPatientDAO lockedDao = DAOFactory.getDAOFactory().createLockedPatientDAO();
        TreatmentDAO treatmentDAO = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            ResultSet lockedPatientRs = lockedDao.getResultSetFromAll();
            while (lockedPatientRs.next()) {
                LOGGER.info("Ist Datum richtig %s = %s", lockedPatientRs.getString(2), LocalDate.now().toString());
                if (lockedPatientRs.getString(2).equals(LocalDate.now().toString())) {
                    LOGGER.info("Datum ist richtig %s = %s", lockedPatientRs.getString(2), LocalDate.now().toString());
                    int PID = lockedPatientRs.getInt(1);
                    lockedDao.deleteById(PID);
                    LOGGER.info("Patient mit PID %s", PID);
                    treatmentDAO.deleteByPid(PID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Die Methode wird aufgerufen, sobald die Patienten oder Treatment ansicht aufgerufen wird.
     * Sie überprüft ob Treatments existieren, die älter als 10 Jahre sind und löscht diese und
     * @throws SQLException
     */
    public void autoDeletePatientsAfterLastTreatment() throws SQLException {
        TreatmentDAO treatmentDao = DAOFactory.getDAOFactory().createTreatmentDAO();
        PatientDAO patientDAO = DAOFactory.getDAOFactory().createPatientDAO();
        try (ResultSet patientRS = patientDAO.getAllPatientsPIDByRS()) {
            int PID;
            while (patientRS.next()) {
                PID = patientRS.getInt(1);
                try (ResultSet treatmentRS = treatmentDao.getPatientsByTreatmentDateDesc(PID)) {
                    try {
                        treatmentRS.next();
                        if (DateConverter.convertStringToLocalDate(treatmentRS.getString(1)).plusYears(10).isBefore(LocalDate.now()) || DateConverter.convertStringToLocalDate(treatmentRS.getString(1)).plusYears(10).isEqual(LocalDate.now())) {
                            treatmentDao.deleteByPid(PID);
                            patientDAO.deleteById(PID);
                        }
                    } catch (SQLException e) {
                    }
                }
            }
        }
    }
}
