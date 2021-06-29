package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Caregivers work in a NURSING home and are treating patients.
 */
public class Caregiver extends Person{
    private long cid;
    private String telnumber;
    private boolean active;

    /**
     * constructs a caregiver from the given params.
     * @param firstName
     * @param surname
     * @param telnumber
     * @param active
     */
    public Caregiver(String firstName, String surname, String telnumber, Boolean active) {
        super(firstName,surname);
        this.telnumber = telnumber;
        this.active = active;
    }

    /**
     * constructs a caregiver from the given params.
     * @param cid
     * @param firstName
     * @param surname
     * @param telnumber
     * @param active
     */
    public Caregiver(long cid, String firstName, String surname, String telnumber, Boolean active) {
        super(firstName,surname);
        this.cid = cid;
        this.telnumber = telnumber;
        this.active = active;
    }

    /**
     *
     * @return caregiver id
     */
    public long getCid() {
        return cid;
    }

    /**
     *
     * @param telnumber new telephone number
     */
    public void setTelnumber(String telnumber) {
        this.telnumber = telnumber;
    }

    /**
     *
     * @return telephone number
     */
    public String getTelnumber() {
        return telnumber;
    }

    /**
     *
     * @param active set new activity state
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     *
     * @return activity status
     */
    public boolean getActive() {
        return active;
    }

    /**
     *
     * @return string-representation of the caregiver
     */
    public String toString() {
        return "Caregiver" + "\nCID: " + this.cid +
                "\nFirstname: " + this.getFirstName() +
                "\nSurname: " + this.getSurname() +
                "\nTelephone: " + this.telnumber +
                "\nActive: " + this.active +
                "\n";
    }
}
