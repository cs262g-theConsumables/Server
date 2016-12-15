package edu.calvin.cs262;

import com.google.gson.Gson;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import javax.sql.rowset.serial.SerialBlob;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

//Get Students, update Students, delete Students (deletes associated Matches but not Messages unless both are deleted)
// get Matches, update Matches, get Messages, make Messages, update Messages,
// delete Messages (if both Students do not exist), get Datedate, make Datedate, update Datedate, delete Datedate

/*
 * This module implements a RESTful service for the student table of the dating database.
 * The server requires Java 1.7 (not 1.8).
 *
 * @author meliornox, LoganVP
 * @version 12/3/16
 */
@Path("/dating")
public class DatingResource {

  /*
   * a hello-world resource
   *
   * @return a simple string value
   */
  @SuppressWarnings("SameReturnValue")
  @GET
  @Path("/hello")
  @Produces("text/plain")
  public String getClichedMessage() {
  return "Hello, Students!";
  }

  /*
   * GET method that returns a particular Student based on an id
   *
   * @param id a student id in the dating database
   * @return a JSON version of the Student record, if any, with the given id
   */
  @GET
  @Path("/student/{id}")
  @Produces("application/json")
  public String getStudent(@PathParam("id") String id) {
    try {
      return new Gson().toJson(retrieveStudent(id));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * GET method that returns a list of all students
   *
   * @return a JSON list representation of the Student records
   */
  @GET
  @Path("/students")
  @Produces("application/json")
  public String getStudents() {
    try {
      return new Gson().toJson(retrieveStudents());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * GET method that returns a list of Matches for a student based on a Student id
   *
   * @param id a student id in the dating database
   * @return a JSON version of a list of Matches, if any, for the given id.
   */
  @GET
  @Path("/matches/{id}")
  @Produces("application/json")
  public String getMatches(@PathParam("id") String id) {
    try {
      makeMatches(id);
      return new Gson().toJson(retrieveMatches(id));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
	
  /*
   * GET method that returns a list of Messages based on two Student ids
   *
   * @param id1 a student id in the dating database
   * @param id2 a student id in the dating database
   * @return a JSON version of a list of Messages, if any, for the given ids.
   */
  @GET
  @Path("/messages/{id1}/{id2}")
  @Produces("application/json")
  public String getMessages(
    @PathParam("id1") String id1,
    @PathParam("id2") String id2) {
    try {
      return new Gson().toJson(retrieveMessages(id1, id2));
    } catch (Exception e) {
      e.printStackTrace();
    }
      return null;
    }
	
  /*
   * GET method that returns a list of Datedates based on two Student ids
   *
   * @param id1 a student id in the dating database
   * @param id2 a student id in the dating database
   * @return a JSON version of a list of Datedates, if any, for the given ids.
   */
  @GET
  @Path("/dates/{id1}/{id2}")
  @Produces("application/json")
  public String getDatedates(
    @PathParam("id1") String id1,
    @PathParam("id2") String id2) {
    try {
      return new Gson().toJson(retrieveDatedates(id1, id2));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * GET method that returns a list of Students based on search parameters
   *
   * @param field the search field
   * @param param the search parameter
   * @return a JSON version of a list of Students, if any, for the given parameters within the field.
   */
  @GET
  @Path("/search/{field}/{param}")
  @Produces("application/json")
  public String getSearch(
    @PathParam("field") String field,
    @PathParam("param") String param) {
    try {
      return new Gson().toJson(retrieveSearch(field, param));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * GET method that returns a list of Students with a certain name
   *
   * @param name the name field
   * @return a JSON version of a list of Students, if any, for the given name.
   */
  @GET
  @Path("/search/{name}")
  @Produces("application/json")
  public String getSearchByName(@PathParam("name") String name) {
    try {
      return new Gson().toJson(retrieveName(name));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * PUT method for creating an instance of Student with a given ID - If the
   * student already exists, update the fields using the new student field values. We do this
   * because PUT is idempotent, meaning that running the same PUT several
   * times is the same as running it exactly once.
   *
   * @param id the ID for the new student, assumed to be unique
   * @param studentLine a JSON representation of the student; the id parameter overrides any id in this line
   * @return JSON representation of the updated student, or NULL for errors
   */
  @PUT
  @Path("/student/{id}")
  @Consumes("application/json")
  @Produces("application/json")
  public String putStudent(@PathParam("id") String id, String studentLine) {
    try {
      Student student = new Gson().fromJson(studentLine, Student.class);
      student.setCalvinID(id);
      return new Gson().toJson(addOrUpdateStudent(student));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
	
  /*
   * PUT method for creating an instance of Datedate with a given ID - If the
   * Datedate already exists, update the fields using the new Datedate field values. We do this
   * because PUT is idempotent, meaning that running the same PUT several
   * times is the same as running it exactly once.
   *
   * @param id the ID for the new Datedate, assumed to be unique
   * @param dateLine a JSON representation of the Datedate; the id parameter overrides any id in this line
   * @return JSON representation of the updated Datedate, or NULL for errors
   */
  @PUT
  @Path("/date/{id}")
  @Consumes("application/json")
  @Produces("application/json")
  public String putDatedate(@PathParam("id") int id, String dateLine) {
    try {
      Datedate date = new Gson().fromJson(dateLine, Datedate.class);
      date.setID(id);
      return new Gson().toJson(addOrUpdateDatedate(date));
      } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
	
  /*
   * PUT method for creating an instance of Message with a given ID - If the
   * message already exists, update the fields using the new message field values. We do this
   * because PUT is idempotent, meaning that running the same PUT several
   * times is the same as running it exactly once.
   *
   * @param id the ID for the new message, assumed to be unique
   * @param messageLine a JSON representation of the message; the id parameter overrides any id in this line
   * @return JSON representation of the updated message, or NULL for errors
   */
  @PUT
  @Path("/message/{id}")
  @Consumes("application/json")
  @Produces("application/json")
  public String putMessage(@PathParam("id") int id, String messageLine) {
    try {
      Message message = new Gson().fromJson(messageLine, Message.class);
      message.setID(id);
      return new Gson().toJson(addOrUpdateMessage(message));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  /*
   * PUT method for creating an instance of Match for a pair of Student ids - If the
   * match already exists, update the fields using the new match field values. We do this
   * because PUT is idempotent, meaning that running the same PUT several
   * times is the same as running it exactly once.
   *
   * @param id1 the first ID for the new match, assumed to be unique
   * @param id2 the second ID for the new match, assumed to be unique
   * @param matchLine a JSON representation of the match; the id1 and id2 parameters override any ids in this line
   * @return JSON representation of the updated match, or NULL for errors
   */
  @PUT
  @Path("/matches/{id1}/{id2}")
  @Consumes("application/json")
  @Produces("application/json")
  public String putMatch(
    @PathParam("id1") String id1,
    @PathParam("id2") String id2,
    String matchLine) {
    try {
      Match match = new Gson().fromJson(matchLine, Match.class);
      match.setACalvinID(id1);
      match.setBCalvinID(id2);
      return new Gson().toJson(addOrUpdateMatch(match));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * POST method for creating an instance of Datedate with a new, unique ID
   * number. We do this because POST is not idempotent, meaning that running
   * the same POST several times creates multiple objects with unique IDs but
   * otherwise having the same field values.
   * <p>
   * The method creates a new, unique ID by querying the Datedate table for the
   * largest ID and adding 1 to that. Using a DB sequence would be a better solution.
   *
   * @param dateLine a JSON representation of the Datedate (ID ignored)
   * @return a JSON representation of the new Datedate
   */
  @POST
  @Path("/date")
  @Consumes("application/json")
  @Produces("application/json")
  public String postDatedate(String dateLine) {
    try {
      Datedate date = new Gson().fromJson(dateLine, Datedate.class);
      return new Gson().toJson(addNewDatedate(date));
    } catch (Exception e) {
      e.printStackTrace();
    }
      return null;
  }

  /*
   * POST method for creating an instance of Message with a new, unique ID
   * number. We do this because POST is not idempotent, meaning that running
   * the same POST several times creates multiple objects with unique IDs but
   * otherwise having the same field values.
   * <p>
   * The method creates a new, unique ID by querying the Message table for the
   * largest ID and adding 1 to that. Using a DB sequence would be a better solution.
   *
   * @param messageLine a JSON representation of the message (ID ignored)
   * @return a JSON representation of the new message
   */
  @POST
  @Path("/message")
  @Consumes("application/json")
  @Produces("application/json")
  public String postMessage(String messageLine) {
    try {
      Message message = new Gson().fromJson(messageLine, Message.class);
      return new Gson().toJson(addNewMessage(message));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * DELETE method for deleting and instance of Student with the given ID. If
   * the student doesn't exist, then don't delete anything. DELETE is idempotent, so
   * the result of sending the same command multiple times should be the same as
   * sending it exactly once.
   *
   * @param id the ID of the student to be deleted
   * @return null
   */
  @DELETE
  @Path("/student/{id}")
  @Produces("application/json")
  public String deleteStudent(@PathParam("id") String id) {
    try {
      Date date = new Date(0);
      Student x = new Student(
        id,
        "deleted",
        "deleted",
        "deleted",
        "deleted",
        "deleted",
        "deleted",
        date,
        "deleted",
        "deleted",
        "deleted",
        "deleted",
        "deleted",
        "deleted",
        "deleted",
        "deleted",
        "deleted",
        "deleted",
        false,
        "deleted",
        false,
        "deleted",
        0,
        "deleted",
        "deleted",
        "deleted",
        0,
        "deleted",
        "deleted",
        'd',
        0,
        "deleted",
        "deleted",
        "deleted",
        "deleted");
      Student y = deleteStudent(x);
      return new Gson().toJson(y);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * DELETE method for deleting an instance of Datedate with the given ID. If
   * the Datedate doesn't exist, then don't delete anything. DELETE is idempotent, so
   * the result of sending the same command multiple times should be the same as
   * sending it exactly once.
   *
   * @param id the ID of the Datedate to be deleted
   * @return null
   */
  @DELETE
  @Path("/date/{id}")
  @Produces("application/json")
  public String deleteDatedate(@PathParam("id") int id) {

    try {
      Datedate x = new Datedate(
        id, 
        "deleted", 
        "deleted", 
        false, 
        false,
        "deleted", 
        "deleted",
        Timestamp.valueOf("0000-00-00 00:00:00.0"));
      Datedate y = deleteDatedate(x);
      return new Gson().toJson(y);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * DELETE method for deleting an instance of Message with the given ID. If
   * the message doesn't exist, then don't delete anything. DELETE is idempotent, so
   * the result of sending the same command multiple times should be the same as
   * sending it exactly once.
   *
   * @param id the ID of the message to be deleted
   * @return null
   */
  @DELETE
  @Path("/message/{id}")
  @Produces("application/json")
  public String deleteMessage(@PathParam("id") int id) {
    try {
      Message x = new Message(
        id, 
        Timestamp.valueOf("0000-00-00 00:00:00.0"), 
        "deleted", 
        "deleted", 
        "deleted");
      Message y = deleteMessage(x);
      return new Gson().toJson(y);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * DELETE method for deleting an instance of Match with the given ID. If
   * the match doesn't exist, then don't delete anything. DELETE is idempotent, so
   * the result of sending the same command multiple times should be the same as
   * sending it exactly once.
   *
   * @param id1 the first ID of the match to be deleted
   * @param id2 the second ID of the match to be deleted
   * @return null
   */
  @DELETE
  @Path("/match/{id1}/{id2}")
  @Produces("application/json")
  public String deleteMatch(
    @PathParam("id1") String id1,
      @PathParam("id2") String id2) {
    try {
        Match x = new Match("deleted", "deleted", "deleted", 0, 0);
      Match y = deleteMatch(x);
      return new Gson().toJson(y);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /* DBMS Utility Functions *********************************************/

  /*
   * Constants for a local Postgresql server with the monopoly database
   */
  private static final String DB_URI = "jdbc:postgresql://localhost:5432/cs262gDating";
  private static final String DB_LOGIN_ID = "postgres";
  private static final String DB_PASSWORD = "Listen-Anywhere-6";
  private static final String PORT = "8087";

  /*
   * Utility method that does the database query, potentially throwing an SQLException,
   * returning a student object (or null).
   */
  private Student retrieveStudent(String id) throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    Student student = null;
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      rs = statement.executeQuery("SELECT * FROM student WHERE CalvinID=" + id);
      if (rs.next()) {
        student = new Student(
          rs.getString(1),
          rs.getString(2),
          rs.getString(3),
          rs.getString(4),
          rs.getString(5),
          rs.getString(6),
          rs.getString(7),
          rs.getDate(8),
          rs.getString(9),
          rs.getString(10),
          rs.getString(11),
          rs.getString(12),
          rs.getString(13),
          rs.getString(14),
          rs.getString(15),
          rs.getString(16),
          rs.getString(17),
          rs.getString(18),
          rs.getBoolean(19),
          rs.getString(20),
          rs.getBoolean(21),
          rs.getString(22),
          rs.getInt(23),
          rs.getString(24),
          rs.getString(25),
          rs.getString(26),
          rs.getInt(27),
          rs.getString(28),
          rs.getString(29),
          rs.getString(30).charAt(0),
          rs.getInt(31),
          rs.getString(32),
          rs.getString(33),
          rs.getString(34),
          rs.getString(35));
        }
    } catch (SQLException e) {
      throw (e);
    } finally {
      rs.close();
      statement.close();
      connection.close();
      }
      return student;
  }

  /*
  * Utility method that does the database query, potentially throwing an SQLException,
  * returning a match object (or null).
  */
  private Match retrieveMatch(String id1, String id2) throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    Match match = null;
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      rs = statement.executeQuery("SELECT * FROM match WHERE (aCalvinID=" + id1 
        + " AND bCalvinID=" + id2
        + ") OR (aCalvinID=" + id2 
        +" AND bCalvinID=" + id1 +")");
      if (rs.next()) {
        match = new Match(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
      }
    } catch (SQLException e) {
      throw (e);
    } finally {
      rs.close();
      statement.close();
      connection.close();
    }
    return match;
  }


  /*
  * Utility method that does the database query, potentially throwing an SQLException,
  * returning a list of name-value map objects (potentially empty).
  */
  private List<Student> retrieveStudents() throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    List<Student> students = new ArrayList<>();
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      rs = statement.executeQuery("SELECT * FROM student");
      while (rs.next()) {
        students.add(new Student(
          rs.getString(1),
          rs.getString(2),
          rs.getString(3),
          rs.getString(4),
          rs.getString(5),
          rs.getString(6),
          rs.getString(7),
          rs.getDate(8),
          rs.getString(9),
          rs.getString(10),
          rs.getString(11),
          rs.getString(12),
          rs.getString(13),
          rs.getString(14),
          rs.getString(15),
          rs.getString(16),
          rs.getString(17),
          rs.getString(18),
          rs.getBoolean(19),
          rs.getString(20),
          rs.getBoolean(21),
          rs.getString(22),
          rs.getInt(23),
          rs.getString(24),
          rs.getString(25),
          rs.getString(26),
          rs.getInt(27),
          rs.getString(28),
          rs.getString(29),
          rs.getString(30).charAt(0),
          rs.getInt(31),
          rs.getString(32),
          rs.getString(33),
          rs.getString(34),
          rs.getString(35)));
        }
    } catch (SQLException e) {
      throw (e);
    } finally {
      rs.close();
      statement.close();
      connection.close();
    }
    return students;
  }

  /*
  * Utility method that does the database query, potentially throwing an SQLException,
  * returning a list of name-value map objects (potentially empty).
  */
  private List<Match> retrieveMatches(String id) throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    List<Match> matches = new ArrayList<>();
    try {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
        statement = connection.createStatement();
        rs = statement.executeQuery("SELECT * FROM match WHERE (aCalvinID=" + id 
          + " OR bCalvinID=" + id 
          + ") AND (aValid=1 AND bValid=1)");
        while (rs.next()) {
        matches.add(new Match(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
      }
    } catch (SQLException e) {
      throw (e);
    } finally {
      rs.close();
      statement.close();
      connection.close();
    }
    return matches;
  }

  /*
  * Utility method that does the database update, potentially throwing an SQLException,
  * returning void.
  */
  private void makeMatches(String id) throws Exception {
    Student studentA = retrieveStudent(id);
    List<Student> students = retrieveStudents();

    //Get list of students and student.  For each element in students, if the element does not equal the student and
    // the match between the students does not exist, search through most of the student parameters and if they are
    // equal make a new match with the parameter as the reason.

    for(Student studentB : students){
      if(((studentB.getCalvinID() != studentA.getCalvinID()) 
        && (retrieveMatch(studentA.getCalvinID(), studentB.getCalvinID()) == null)) 
        && ((studentB.getGender() == studentA.getGenderWant()) 
        && (studentA.getGender() == studentB.getGenderWant()))){
        String reason = null;
        if(studentA.getClassYear() == studentB.getClassYear()){
          reason = "Same class year";
        }else if(studentA.getMbti() == studentB.getMbti()){
          reason = "Same major department";
        }else if(studentA.getTulip() == studentB.getTulip()){
          reason = "Same major department";
        }else if(studentA.getHateHope() == studentB.getHateHope()){
          reason = "Same major department";
        }else if(studentA.getBQuiv() == studentB.getBQuiv()){
          reason = "Same major department";
        }else if(studentA.getDiningPreference() == studentB.getDiningPreference()){
          reason = "Same major department";
        }else if(studentA.getBunHate() == studentB.getBunHate()){
          reason = "Same major department";
        }else if(studentA.getChapelDay() == studentB.getChapelDay()){
          reason = "Same major department";
        }else if(studentA.getLoft() == studentB.getLoft()){
          reason = "Same major department";
        }
        if(reason != null){
          Match match = new Match(
            studentA.getCalvinID(), 
            studentB.getCalvinID(), 
            reason,
            0,
            0);
            addOrUpdateMatch(match);
        }
      }
    }
  }

  /*
  * Utility method that does the database query, potentially throwing an SQLException,
  * returning a list of name-value map objects (potentially empty).
  */
  private List<Message> retrieveMessages(String id1, String id2) throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    List<Message> messages = new ArrayList<>();
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      rs = statement.executeQuery("SELECT * FROM message WHERE (toID=" + id1 
        + " AND fromID=" + id2 
        +") OR (toID=" + id2 
        + " AND fromID=" + id1 +")");
      while (rs.next()) {
        messages.add(new Message(
          rs.getInt(1),
          rs.getTimestamp(2), 
          rs.getString(3), 
          rs.getString(4),
          rs.getString(5)));
      }
    } catch (SQLException e) {
      throw (e);
    } finally {
      rs.close();
      statement.close();
      connection.close();
    }
    return messages;
  }

  /*
  * Utility method that does the database query, potentially throwing an SQLException,
  * returning a list of name-value map objects (potentially empty).
  */
  private List<Datedate> retrieveDatedates(String id1, String id2) throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    List<Datedate> dates = new ArrayList<>();
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      rs = statement.executeQuery("SELECT * FROM datedate WHERE (aCalvinID=" + id1 
        + " AND bCalvinID=" + id2
        +") OR aCalvinID=" + id2 
        + " AND bCalvinID=" + id1 +")");
      while (rs.next()) {
        dates.add(new Datedate(
          rs.getInt(1),
          rs.getString(2),
          rs.getString(3), 
          rs.getBoolean(4), 
          rs.getBoolean(5),
          rs.getString(6), 
          rs.getString(7), 
          rs.getTimestamp(8)));
      }
    } catch (SQLException e) {
      throw (e);
    } finally {
      rs.close();
      statement.close();
      connection.close();
    }
    return dates;
  }

  /*
  * Utility method that does the database query, potentially throwing an SQLException,
  * returning a list of name-value map objects (potentially empty).
  */
  private List<Student> retrieveSearch(String field, String param) throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    List<Student> result = new ArrayList<>();
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      rs = statement.executeQuery("SELECT * FROM Student WHERE " + field + "=" + param);
      while (rs.next()) {
        result.add(new Student(
          rs.getString(1),
          rs.getString(2),
          rs.getString(3),
          rs.getString(4),
          rs.getString(5),
          rs.getString(6),
          rs.getString(7),
          rs.getDate(8),
          rs.getString(9),
          rs.getString(10),
          rs.getString(11),
          rs.getString(12),
          rs.getString(13),
          rs.getString(14),
          rs.getString(15),
          rs.getString(16),
          rs.getString(17),
          rs.getString(18),
          rs.getBoolean(19),
          rs.getString(20),
          rs.getBoolean(21),
          rs.getString(22),
          rs.getInt(23),
          rs.getString(24),
          rs.getString(25),
          rs.getString(26),
          rs.getInt(27),
          rs.getString(28),
          rs.getString(29),
          rs.getString(30).charAt(0),
          rs.getInt(31),
          rs.getString(32),
          rs.getString(33),
          rs.getString(34),
          rs.getString(35)));
      }
    } catch (SQLException e) {
      throw (e);
    } finally {
      rs.close();
      statement.close();
      connection.close();
    }
    return result;
  }

  /*
  * Utility method that does the database query, potentially throwing an SQLException,
  * returning a list of name-value map objects (potentially empty).
  */
  private List<Student> retrieveName(String name) throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    List<Student> result = new ArrayList<>();
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      if(name.indexOf(' ') > 0){
        String[] array = name.split(" ");
        String firstName = array[0];
        String lastName = array[1];
        rs = statement.executeQuery("SELECT * FROM student WHERE first=" + firstName 
          + " AND last=" + lastName);
      } else {
        rs = statement.executeQuery("SELECT * FROM student WHERE first=" + name 
          + " OR last=" + name);
      }
      while (rs.next()) {
        result.add(new Student(
          rs.getString(1),
          rs.getString(2),
          rs.getString(3),
          rs.getString(4),
          rs.getString(5),
          rs.getString(6),
          rs.getString(7),
          rs.getDate(8),
          rs.getString(9),
          rs.getString(10),
          rs.getString(11),
          rs.getString(12),
          rs.getString(13),
          rs.getString(14),
          rs.getString(15),
          rs.getString(16),
          rs.getString(17),
          rs.getString(18),
          rs.getBoolean(19),
          rs.getString(20),
          rs.getBoolean(21),
          rs.getString(22),
          rs.getInt(23),
          rs.getString(24),
          rs.getString(25),
          rs.getString(26),
          rs.getInt(27),
          rs.getString(28),
          rs.getString(29),
          rs.getString(30).charAt(0),
          rs.getInt(31),
          rs.getString(32),
          rs.getString(33),
          rs.getString(34),
          rs.getString(35)));
      }
    } catch (SQLException e) {
      throw (e);
    } finally {
      rs.close();
      statement.close();
      connection.close();
    }
    return result;
  }

  /*
  * Utility method that does the database update, potentially throwing an SQLException,
  * returning the student, potentially new.
  */
  private Student addOrUpdateStudent(Student student) throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      rs = statement.executeQuery("SELECT * FROM student WHERE CalvinID=" 
        + student.getCalvinID());
      if (rs.next()) {
        statement.executeUpdate(
          "UPDATE student SET password='" + student.getPassword() +
          "', picture='" + student.getPicture() +
          "', first='" + student.getFirst() +
          "', last='" + student.getLast() +
          "', username='" + student.getUsername() +
          "', classYear='" + student.getClassYear() +
          "', birthday='" + student.getBirthday() +
          "', homeCity='" + student.getHomeCity() +
          "', homeState='" + student.getHomeState() +
          "', homeCountry='" + student.getHomeCountry() +
          "', major='" + student.getMajor() +
          "', majorDepartment='" + student.getMajorDepartment() +
          "', majorNumber='" + student.getMajorNumber() +
          "', gender='" + student.getGender() +
          "', genderWant='" + student.getGenderWant() +
          "', religion='" + student.getReligion() +
          "', mbti='" + student.getMbti() +
          "', hasJob='" + student.getHasJob() +
          "', job='" + student.getJob() +
          "', calvinT='" + student.getTulip() +
          "', hangout='" + student.getHangout() +
          "', hateHope='" + student.getHateHope() +
          "', bQuiv='" + student.getBQuiv() +
          "', diningPreference='" + student.getDiningPreference() +
          "', sports='" + student.getSports() +
          "', bunHate='" + student.getBunHate() +
          "', studySpot='" + student.getStudySpot() +
          "', chapelDay='" + student.getChapelDay() +
          "', loft='" + student.getLoft() +
          "', height='" + student.getHeight() +
          "', nationality='" + student.getNationality() +
          "', vocation='" + student.getVocation() +
          "', aboutme='" + student.getAboutMe() +
          "', status='" + student.getStatus() +
          "' WHERE CalvinID='" + student.getCalvinID() +
          "'");
      } else {
        statement.executeUpdate(
          "INSERT INTO student VALUES ('" + student.getCalvinID() +
          "', '" + student.getPassword() +
          "', '" + student.getPicture() +
          "', '" + student.getFirst() +
          "', '" + student.getLast() +
          "', '" + student.getUsername() +
          "', '" + student.getClassYear() +
          "', '" + student.getBirthday() +
          "', '" + student.getHomeCity() +
          "', '" + student.getHomeState() +
          "', '" + student.getHomeCountry() +
          "', '" + student.getMajor() +
          "', '" + student.getMajorDepartment() +
          "', '" + student.getMajorNumber() +
          "', '" + student.getGender() +
          "', '" + student.getReligion() +
          "', '" + student.getMbti() +
          "', '" + student.getHasJob() +
          "', '" + student.getJob() +
          "', '" + student.getTulip() +
          "', '" + student.getHangout() +
          "', '" + student.getHateHope() +
          "', '" + student.getBQuiv() +
          "', '" + student.getDiningPreference() +
          "', '" + student.getSports() +
          "', '" + student.getBunHate() +
          "', '" + student.getStudySpot() +
          "', '" + student.getChapelDay() +
          "', '" + student.getLoft() +
          "', '" + student.getHeight() +
          "', '" + student.getNationality() +
          "', '" + student.getVocation() +
          "', '" + student.getAboutMe() +
          "', '" + student.getStatus() +
          "')");
      }
    } catch (SQLException e) {
      throw (e);
    } finally {
      rs.close();
      statement.close();
      connection.close();
    }
    return student;
  }

  /*
  * Utility method that does the database update, potentially throwing an SQLException,
  * returning the match, potentially new.
  */
  private Match addOrUpdateMatch(Match match) throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      rs = statement.executeQuery("SELECT * FROM match WHERE (aCalvinID=" + match.getACalvinID()
        + " AND bCalvinID=" + match.getBCalvinID() 
        +") OR (aCalvinID=" + match.getBCalvinID()
        + " AND bCalvinID=" + match.getACalvinID() +")");
      if (rs.next()) {
        statement.executeUpdate("UPDATE match SET aCalvinID='" + match.getACalvinID() 
        + "', bCalvinID='" + match.getBCalvinID() 
        + "', reason='" + match.getReason() 
        + "', aValid=" + match.getAValid()
        + ", bValid=" + match.getBValid() 
        + " WHERE (aCalvinID=" + match.getACalvinID()
        + " AND bCalvinID="  + match.getBCalvinID() 
        +") OR (aCalvinID=" + match.getBCalvinID()
        + " AND bCalvinID=" + match.getACalvinID() +")");
      } else {
        statement.executeUpdate("INSERT INTO match VALUES ('" 
          + match.getACalvinID() + "', '"
          + match.getBCalvinID() + "', '" 
          + match.getReason() + "', " 
          + match.getAValid() + ", "
          + match.getBValid() + "')");
      }
    } catch (SQLException e) {
      throw (e);
    } finally {
      rs.close();
      statement.close();
      connection.close();
    }
    return match;
  }

  /*
  * Utility method that does the database update, potentially throwing an SQLException,
  * returning the message, potentially new.
  */
  private Message addOrUpdateMessage(Message message) throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      rs = statement.executeQuery("SELECT * FROM message WHERE ID=" + message.getID());
      if (rs.next()) {
        statement.executeUpdate("UPDATE message SET timestamp=" + message.getTimestamp() 
          + ", toID='" + message.getToID() 
          + "', fromID='" + message.getFromID() 
          + "', message='" + message.getMessage() 
          + "' WHERE ID=" + message.getID());
      } else {
        statement.executeUpdate("INSERT INTO message VALUES (" 
          + message.getID() + ", " 
          + message.getTimestamp() + ", '" 
          + message.getToID() + "', '" 
          + message.getFromID() + "', '" 
          + message.getMessage() + "')");
      }
    } catch (SQLException e) {
      throw (e);
    } finally {
      rs.close();
      statement.close();
      connection.close();
    }
    return message;
  }

  /*
  * Utility method that does the database update, potentially throwing an SQLException,
  * returning the date, potentially new.
  */
  private Datedate addOrUpdateDatedate(Datedate date) throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
        rs = statement.executeQuery("SELECT * FROM datedate WHERE ID=" + date.getID());
        if (rs.next()) {
        statement.executeUpdate("UPDATE datedate SET aCalvinID='" + date.getACalvinID() 
          + "', toID='" + date.getBCalvinID() 
          + "', aAccept=" + date.getAAccept() 
          + ", bAccept=" + date.getBAccept()
          + ", place='" + date.getPlace()
          + "', activity='" + date.getActivity() 
          + "', timestamp=" + date.getTimestamp() 
          + " WHERE ID=" + date.getID());
      } else {
        statement.executeUpdate("INSERT INTO datedate VALUES (" 
          + date.getID()  + ", " 
          + date.getACalvinID() + "', '"
           date.getBCalvinID() + "', " 
          + date.getAAccept() + ", " 
          + date.getBAccept() + ", '"
          + date.getPlace() + "', '" 
          + date.getActivity() + "', " 
          + date.getTimestamp() + "')");
      }
    } catch (SQLException e) {
      throw (e);
    } finally {
      rs.close();
      statement.close();
      connection.close();
    }
    return date;
  }

  /*
  * Utility method that adds the given message using a new,unique ID, potentially throwing an SQLException,
  * returning the new message
  */
  private Message addNewMessage(Message message) throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      rs = statement.executeQuery("SELECT MAX(ID) FROM message");
      if (rs.next()) {
        message.setID(rs.getInt(1) + 1);
      } else {
        throw new RuntimeException("failed to find unique ID...");
      }
      statement.executeUpdate("INSERT INTO message VALUES (" 
        + message.getID() + ", " 
        + message.getTimestamp() + ", '" 
        + message.getToID() + "', '" 
        + message.getFromID() + "', '" 
        + message.getMessage() + "')");
    } catch (SQLException e) {
      throw (e);
    } finally {
      rs.close();
      statement.close();
      connection.close();
    }
    return message;
  }

  /*
  * Utility method that adds the given date using a new,unique ID, potentially throwing an SQLException,
  * returning the new date
  */
  private Datedate addNewDatedate(Datedate date) throws Exception {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      rs = statement.executeQuery("SELECT MAX(ID) FROM date");
      if (rs.next()) {
        date.setID(rs.getInt(1) + 1);
      } else {
        throw new RuntimeException("failed to find unique ID...");
      }
      statement.executeUpdate("INSERT INTO atedate VALUES (" 
        + date.getID() + ", " 
        + date.getACalvinID() + "', '"
        + date.getBCalvinID() + "', " 
        + date.getAAccept() + ", " 
        + date.getBAccept() + ", '"
        + date.getPlace() + "', '" 
        + date.getActivity() + "', " 
        + date.getTimestamp() + "')");
    } catch (SQLException e) {
      throw (e);
    } finally {
      rs.close();
      statement.close();
      connection.close();
    }
    return date;
  }

  /*
  * Utility method that does the database update, potentially throwing an SQLException,
  * returning the student, potentially new.
  */
  public Student deleteStudent(Student student) throws Exception {
    Connection connection = null;
    Statement statement = null;
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      statement.executeUpdate("DELETE FROM student WHERE CalvinID='" 
        + student.getCalvinID() + "'");
    } catch (SQLException e) {
      throw (e);
    } finally {
      statement.close();
      connection.close();
    }
    return student;
  }

  /*
  * Utility method that does the database update, potentially throwing an SQLException,
  * returning the match, potentially new.
  */
  public Match deleteMatch(Match match) throws Exception {
    Connection connection = null;
    Statement statement = null;
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      statement.executeUpdate("DELETE FROM match WHERE (aCalvinID='" + match.getACalvinID() 
        + "' AND bCalvinID='" + match.getBCalvinID() 
        + "') OR (aCalvinID='" + match.getBCalvinID() 
        + "' AND bCalvinID='" + match.getACalvinID() + "')");
    } catch (SQLException e) {
      throw (e);
    } finally {
      statement.close();
      connection.close();
        }
    return match;
  }

  /*
  * Utility method that does the database update, potentially throwing an SQLException,
  * returning the message, potentially new.
  */
  public Message deleteMessage(Message message) throws Exception {
    Connection connection = null;
    Statement statement = null;
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      statement.executeUpdate("DELETE FROM message WHERE ID=" + message.getID());
    } catch (SQLException e) {
      throw (e);
    } finally {
      statement.close();
      connection.close();
    }
    return message;
  }

  /*
  * Utility method that does the database update, potentially throwing an SQLException,
  * returning the date, potentially new.
  */
  public Datedate deleteDatedate(Datedate date) throws Exception {
    Connection connection = null;
    Statement statement = null;
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
      statement = connection.createStatement();
      statement.executeUpdate("DELETE FROM datedate WHERE ID=" + date.getID());
    } catch (SQLException e) {
      throw (e);
    } finally {
      statement.close();
      connection.close();
    }
    return date;
  }


  /* Main *****************************************************/

  /*
   * Run this main method to fire up the service.
   *
   * @param args command-line arguments (ignored)
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    HttpServer server = HttpServerFactory.create("http://localhost:" + PORT + "/");
    server.start();

    System.out.println("Server running...");
    System.out.println("Web clients should visit: http://cs262.cs.calvin.edu:" 
      + PORT + "/dating");
    System.out.println("Android emulators should visit: http://cs262.cs.calvin.edu:" 
      + PORT + "/dating");
    System.out.println("Hit return to stop...");
    //noinspection ResultOfMethodCallIgnored
    System.in.read();
    System.out.println("Stopping server...");
    server.stop(0);
    System.out.println("Server stopped...");
  }
}