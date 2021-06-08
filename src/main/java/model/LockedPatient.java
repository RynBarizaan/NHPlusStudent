package model;

import java.time.LocalDate;

/**
 * Patients live in a NURSING home and are treated by nurses.
 */

public class LockedPatient extends Patient{
    private LocalDate toDeleteDate;

    /**
     * constructs a patient from the given params.
     * @param firstName
     * @param surname
     * @param dateOfBirth
     * @param careLevel
     * @param roomnumber
     * @param toDeleteDate
     */

    public LockedPatient(String firstName, String surname, LocalDate dateOfBirth, String careLevel, String roomnumber, LocalDate toDeleteDate) {
        super(firstName, surname, dateOfBirth, careLevel, roomnumber);
        this.toDeleteDate = toDeleteDate;
    }

    /**
     * constructs a patient from the given params.
     * @param pid
     * @param firstName
     * @param surname
     * @param dateOfBirth
     * @param careLevel
     * @param roomnumber
     * @param toDeleteDate
     */

    public LockedPatient(long pid, String firstName, String surname, LocalDate dateOfBirth, String careLevel, String roomnumber, LocalDate toDeleteDate) {
        super(pid, firstName, surname, dateOfBirth, careLevel, roomnumber);
        this.toDeleteDate = toDeleteDate;
    }

    /**
     *
     * @return toDeleteDate
     */

    public LocalDate getToDeleteDate() {
        return toDeleteDate;
    }

    /**
     *
     * @param toDeleteDate new deletion date
     */

    public void setToDeleteDate(LocalDate toDeleteDate) {
        this.toDeleteDate = toDeleteDate;
    }
}
