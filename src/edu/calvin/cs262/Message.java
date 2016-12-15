package edu.calvin.cs262;

import java.sql.Timestamp;

/**
 * A User class for the Message relation
 *
 * @author Loganvp, meliornox
 * @version 12/3/16
 */
class Message {

  private int ID;
  private Timestamp timestamp;
  private String toID, fromID, message;

  @SuppressWarnings("unused")
  Message() { /* a default constructor, required by Gson */ }

  Message(int ID, Timestamp timestamp, String toID, String fromID, String message) {

    this.ID = ID;
    this.timestamp = timestamp;
    this.toID = toID;
    this.fromID = fromID;
    this.message = message;

  }

  /*
   *  GET methods
   */
  @SuppressWarnings("unused")
  public int getID() {
     return ID; 
  }

  @SuppressWarnings("unused")
  public Timestamp getTimestamp() {
     return timestamp; 
  }

  @SuppressWarnings("unused")
  public String getToID() {
     return toID; 
  }

  @SuppressWarnings("unused")
  public String getFromID() {
     return fromID; 
  }

  @SuppressWarnings("unused")
  public String getMessage() {
     return message; 
  }

  /*
   * SET Methods
   */
  @SuppressWarnings("unused")
  public void setID(int ID) {
     this.ID = ID; 
  }

  @SuppressWarnings("unused")
  public void getTimestamp(Timestamp timestamp) {
     this.timestamp = timestamp; 
  }

  @SuppressWarnings("unused")
  public void getToID(String toID) {
     this.toID = toID; 
  }

  @SuppressWarnings("unused")
  public void getFromID(String fromID) {
     this.fromID = fromID; 
  }

  @SuppressWarnings("unused")
  public void getMessage(String message) {
    this.message = message; 
  }
}
