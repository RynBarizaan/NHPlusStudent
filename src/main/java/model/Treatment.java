package model;

import utils.DateConverter;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Every treatment belongs to a patient and is accompanied by a caregiver
 */
public class Treatment {
    private long tid;
    private long pid;
    private long cid;
    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;
    private String description;
    private String remarks;

    /**
     *
     * @param pid
     * @param cid
     * @param date
     * @param begin
     * @param end
     * @param description
     * @param remarks
     */
    public Treatment(long pid, long cid, LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks) {
        this.pid = pid;
        this.cid = cid;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
    }

    /**
     *
     * @param tid
     * @param pid
     * @param cid
     * @param date
     * @param begin
     * @param end
     * @param description
     * @param remarks
     */
    public Treatment(long tid, long pid, long cid, LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks) {
        this.tid = tid;
        this.cid = cid;
        this.pid = pid;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
    }

    /**
     *
     * @return tid
     */
    public long getTid() {
        return tid;
    }

    /**
     *
     * @return pid
     */
    public long getPid() {
        return this.pid;
    }

    /**
     *
     * @return cid
     */
    public long getCid() {return this.cid;}

    /**
     *
     * @param cid set new caregiver id
     */
    public void setCid(long cid) {
        this.cid = cid;
    }

    /**
     *
     * @return date
     */
    public String getDate() {
        return date.toString();
    }

    /**
     *
     * @return begin time
     */
    public String getBegin() {
        return begin.toString();
    }

    /**
     *
     * @return end time
     */
    public String getEnd() {
        return end.toString();
    }

    /**
     *
     * @param s_date set new date
     */
    public void setDate(String s_date) {
        LocalDate date = DateConverter.convertStringToLocalDate(s_date);
        this.date = date;
    }

    /**
     *
     * @param begin set new begin time
     */
    public void setBegin(String begin) {
        LocalTime time = DateConverter.convertStringToLocalTime(begin);
        this.begin = time;
    }

    /**
     *
     * @param end set new end time
     */
    public void setEnd(String end) {
        LocalTime time = DateConverter.convertStringToLocalTime(end);
        this.end = time;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description set new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     *
     * @param remarks set new remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     *
     * @return string-representation of the treatment
     */
    public String toString() {
        return "\nBehandlung" + "\nTID: " + this.tid +
                "\nPID: " + this.pid +
                "\nCID: " + this.cid +
                "\nDate: " + this.date +
                "\nBegin: " + this.begin +
                "\nEnd: " + this.end +
                "\nDescription: " + this.description +
                "\nRemarks: " + this.remarks + "\n";
    }
}