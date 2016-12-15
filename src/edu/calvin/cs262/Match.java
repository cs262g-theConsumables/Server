package edu.calvin.cs262;

/**
 * A User class for the Match relation
 *
 * @author Loganvp, meliornox
 * @version 12/3/16
 */
class Match {

  private String aCalvinID, bCalvinID, reason;
  private int aValid, bValid;


  @SuppressWarnings("unused")
  Match() { /* a default constructor, required by Gson */ }

  Match(String aCalvinID, String bCalvinID, String reason, int aValid, int bValid) {

    this.aCalvinID = aCalvinID;
    this.bCalvinID = bCalvinID;
    this.reason = reason;
    this.aValid = aValid;
    this.bValid = bValid;

  }
  /*
   *  GET methods
   */
  @SuppressWarnings("unused")
  public String getACalvinID() {
    return aCalvinID; 
  }

  @SuppressWarnings("unused")
  public String getBCalvinID() {
    return bCalvinID; 
  }

  @SuppressWarnings("unused")
  public String getReason() {
    return reason; 
  }

  @SuppressWarnings("unused")
  public int getAValid() {
    return aValid; 
  }

  @SuppressWarnings("unused")
  public int getBValid() {
    return bValid; 
  }

  /*
   * SET Methods
   */
  @SuppressWarnings("unused")
  public void setACalvinID(String aCalvinID) {
    this.aCalvinID = aCalvinID; 
  }

  @SuppressWarnings("unused")
  public void setBCalvinID(String bCalvinID) {
    this.bCalvinID = bCalvinID; 
  }

  @SuppressWarnings("unused")
  public void setReason(String reason) {
    this.reason = reason; 
  }

  @SuppressWarnings("unused")
  public void setAValid(int aValid) {
    this.aValid = aValid; 
  }

  @SuppressWarnings("unused")
  public void setBValid(int bValid) {
    this.bValid = bValid; 
  }
}
