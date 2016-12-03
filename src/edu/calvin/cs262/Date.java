package edu.calvin.cs262;

import java.sql.Date;

/**
 * A User class for the date relation
 *
 * @author Loganvp
 * @version 12/3/16
 */
class Date {

    private String aCalvinID, bCalvinID, place, activity;
    private boolean aAccept, bAccept;
    private time timestamp;


    @SuppressWarnings("unused")
    Date() { /* a default constructor, required by Gson */ }

    Date(String aCalvinID, String aCalvinID, boolean aAccept, boolean bAccept,String place, String activity, time timestamp) {

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
    public String getaCalvinID() { return aCalvinID; }

    @SuppressWarnings("unused")
    public String getbCalvinID() { return bCalvinID; }

    @SuppressWarnings("unused")
    public boolean getaAccept() { return aAccept; }

    @SuppressWarnings("unused")
    public boolean getbAccept() { return bAccept; }

    @SuppressWarnings("unused")
    public String getPlace() { return place; }

    @SuppressWarnings("unused")
    public String getActivity() { return activity; }

    @SuppressWarnings("unused")
    public time getTime() { return timestamp; }

    /*
     * SET Methods
     */
    @SuppressWarnings("unused")
    public void setaCalvinID(String aCalvinID) { this.aCalvinID = aCalvinID; }

    @SuppressWarnings("unused")
    public void setbCalvinID(String bCalvinID) { this.bCalvinID = bCalvinID; }

    @SuppressWarnings("unused")
    public void setaAccept(boolean aAccept) { this.aAccept = aAccept; }

    @SuppressWarnings("unused")
    public void setbAccept(boolean bAccept) { this.bAccept = bAccept; }

    @SuppressWarnings("unused")
    public void setPlace(String place) { this.place = place; }

    @SuppressWarnings("unused")
    public void setActivity(String activity) { this.activity = activity; }

    @SuppressWarnings("unused")
    public void setTime(time timestamp) { this.timestamp = timestamp }
}
