package edu.calvin.cs262;

import java.sql.Timestamp;

/**
 * A User class for the datedate relation
 *
 * @author Loganvp, meliornox
 * @version 12/3/16
 */
class Datedate {

    private int ID;
    private String aCalvinID, bCalvinID, place, activity;
    private boolean aAccept, bAccept;
    private Timestamp timestamp;


    @SuppressWarnings("unused")
    Datedate() { /* a default constructor, required by Gson */ }

    Datedate(int ID, String aCalvinID, String bCalvinID, boolean aAccept, boolean bAccept,String place, String activity,
         Timestamp timestamp) {

        this.ID = ID;
        this.aCalvinID = aCalvinID;
        this.bCalvinID = bCalvinID;
        this.aAccept = aAccept;
        this.bAccept = bAccept;
        this.place = place;
        this.activity = activity;
        this.timestamp = timestamp;

    }
    /*
     *  GET methods
     */
    @SuppressWarnings("unused")
    public int getID() { return ID; }

    @SuppressWarnings("unused")
    public String getACalvinID() { return aCalvinID; }

    @SuppressWarnings("unused")
    public String getBCalvinID() { return bCalvinID; }

    @SuppressWarnings("unused")
    public boolean getAAccept() { return aAccept; }

    @SuppressWarnings("unused")
    public boolean getBAccept() { return bAccept; }

    @SuppressWarnings("unused")
    public String getPlace() { return place; }

    @SuppressWarnings("unused")
    public String getActivity() { return activity; }

    @SuppressWarnings("unused")
    public Timestamp getTimestamp() { return timestamp; }

    /*
     * SET Methods
     */
    @SuppressWarnings("unused")
    public void setID(int ID) { this.ID = ID; }

    @SuppressWarnings("unused")
    public void setACalvinID(String aCalvinID) { this.aCalvinID = aCalvinID; }

    @SuppressWarnings("unused")
    public void setBCalvinID(String bCalvinID) { this.bCalvinID = bCalvinID; }

    @SuppressWarnings("unused")
    public void setAAccept(boolean aAccept) { this.aAccept = aAccept; }

    @SuppressWarnings("unused")
    public void setBAccept(boolean bAccept) { this.bAccept = bAccept; }

    @SuppressWarnings("unused")
    public void setPlace(String place) { this.place = place; }

    @SuppressWarnings("unused")
    public void setActivity(String activity) { this.activity = activity; }

    @SuppressWarnings("unused")
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
}
