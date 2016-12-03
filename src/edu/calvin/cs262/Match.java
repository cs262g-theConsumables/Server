package edu.calvin.cs262;

import java.sql.Date;

/**
 * A User class for the Match relation
 *
 * @author Loganvp
 * @version 12/3/16
 */
class Match {

    private String aCalvinID, bCalvinID, reason;
    private int aValid, bValid;
    private Date date;


    @SuppressWarnings("unused")
    Match() { /* a default constructor, required by Gson */ }

    Match(String aCalvinID, String aCalvinID, String reason, int aValid, int bValid, Date date) {

        this.aCalvinID = aCalvinID;
        this.bCalvinID = bCalvinID;
        this.reason = reason;
        this.aValid = aValid;
        this.bValid = bValid;
        this.date = date;

    }
    /*
     *  GET methods
     */
    @SuppressWarnings("unused")
    public String getaCalvinID() { return aCalvinID; }

    @SuppressWarnings("unused")
    public String getbCalvinID() { return bCalvinID; }

    @SuppressWarnings("unused")
    public String getReason() { return reason; }

    @SuppressWarnings("unused")
    public int getaValid() { return aValid; }

    @SuppressWarnings("unused")
    public int getbValid() { return bValid; }

    @SuppressWarnings("unused")
    public Date getDate() { return date; }

    /*
     * SET Methods
     */
    @SuppressWarnings("unused")
    public void setaCalvinID(String aCalvinID) { this.aCalvinID = aCalvinID; }

    @SuppressWarnings("unused")
    public void setbCalvinID(String bCalvinID) { this.bCalvinID = bCalvinID; }

    @SuppressWarnings("unused")
    public void setReason(String reason) { this.reason = reason; }

    @SuppressWarnings("unused")
    public void setaValid(int aValid) { this.aValid = aValid; }

    @SuppressWarnings("unused")
    public void setbValid(int bValid) { this.bValid = bValid; }

    @SuppressWarnings("unused")
    public void setDate(Date date) { this.date = date }
}
