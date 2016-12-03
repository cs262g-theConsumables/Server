package edu.calvin.cs262;


/**
 * A User class for the StudentPicture relation
 *
 * @author Loganvp
 * @version 12/3/16
 */
class StudentPicture {

    private String CalvinID;
    private bytea picture;


    @SuppressWarnings("unused")
    StudentPicture() { /* a default constructor, required by Gson */ }

    StudentPicture(String CalvinID, blob picture) {

        this.CalvinID = CalvinID;
        this.picture = picture;

    }
    /*
     *  GET methods
     */
    @SuppressWarnings("unused")
    public String getCalvinID() { return CalvinID; }

    @SuppressWarnings("unused")
    public String getBlob() { return picture; }

    /*
     * SET Methods
     */

    @SuppressWarnings("unused")
    public void setCalvinID(String CalvinID) { this.CalvinID = CalvinID; }

    @SuppressWarnings("unused")
    public void setBlob(blob picture) { this.picture = picture; }
}
