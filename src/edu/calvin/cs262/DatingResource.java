package edu.calvin.cs262;

import com.google.gson.Gson;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

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
import java.util.*;

//Get Students, ~make Students~, update Students, delete Students (deletes associated Matches but not Messages unless both are deleted) get Matches, ~make Matches~, update Matches, get Messages, make Messages
// update Messages, delete Messages (if both Students do not exist), get Date, make Date, update Date, delete Date

/**
 * This module implements a RESTful service for the student table of the dating database.
 * The server requires Java 1.7 (not 1.8).
 *
 * @author meliornox
 * @version fall, 2016 - version 0.1
 */
@Path("/dating")
public class DatingResource {

    /**
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
	
    /**
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

    /**
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

	/**
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
            return new Gson().toJson(retrieveMatches(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	/**
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
		@PathParam("id2") String id2) 
	{
		try {
			return new Gson().toJson(retrieveMessages(id1, id2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/**
     * GET method that returns a list of Dates based on two Student ids
     *
     * @param id1 a student id in the dating database
     * @param id2 a student id in the dating database
     * @return a JSON version of a list of Dates, if any, for the given ids.
     */
    @GET
    @Path("/dates/{id1}/{id2}")
    @Produces("application/json")
    public String getDates(
		@PathParam("id1") String id1,
		@PathParam("id2") String id2) 
	{
        try {
            return new Gson().toJson(retrieveDates(id1, id2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
    /**
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
            Student student = new Gson().fromJson(student, Student.class);
            student.setId(id);
            return new Gson().toJson(addOrUpdateStudent(student));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
    /**
     * PUT method for creating an instance of Date with a given ID - If the
     * date already exists, update the fields using the new date field values. We do this
     * because PUT is idempotent, meaning that running the same PUT several
     * times is the same as running it exactly once.
     *
     * @param id the ID for the new date, assumed to be unique
     * @param dateLine a JSON representation of the date; the id parameter overrides any id in this line
     * @return JSON representation of the updated date, or NULL for errors
     */
    @PUT
    @Path("/date/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public String putDate(@PathParam("id") int id, String dateLine) {
        try {
            Date date = new Gson().fromJson(date, Date.class);
            date.setId(id);
            return new Gson().toJson(addOrUpdateMessage(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
    /**
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
            Message message = new Gson().fromJson(message, Message.class);
            message.setId(id);
            return new Gson().toJson(addOrUpdateMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
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
		@PathParam("id2") String id2) 
	{
        try {
            Match match = new Gson().fromJson(match, Match.class);
            match.setId1(id1);
            match.setId1(id1);
            return new Gson().toJson(addOrUpdateMatch(match));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * POST method for creating an instance of Date with a new, unique ID
     * number. We do this because POST is not idempotent, meaning that running
     * the same POST several times creates multiple objects with unique IDs but
     * otherwise having the same field values.
     * <p>
     * The method creates a new, unique ID by querying the Date table for the
     * largest ID and adding 1 to that. Using a DB sequence would be a better solution.
     *
     * @param dateLine a JSON representation of the date (ID ignored)
     * @return a JSON representation of the new date
     */
    @POST
    @Path("/date")
    @Consumes("application/json")
    @Produces("application/json")
    public String postDate(String dateLine) {
        try {
            Date date = new Gson().fromJson(dateLine, Date.class);
            return new Gson().toJson(addNewDate(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
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

    /**
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
            Student x = new Student(id, "deleted", "deleted");
            Student y = deleteStudent(x);
            return new Gson().toJson(y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DELETE method for deleting an instance of Date with the given ID. If
     * the date doesn't exist, then don't delete anything. DELETE is idempotent, so
     * the result of sending the same command multiple times should be the same as
     * sending it exactly once.
     *
     * @param id the ID of the date to be deleted
     * @return null
     */
    @DELETE
    @Path("/date/{id}")
    @Produces("application/json")
    public String deleteDate(@PathParam("id") int id) {
        try {
            Date x = new Date(id, "deleted", "deleted");
            Date y = deleteDate(x);
            return new Gson().toJson(y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
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
            Message x = new Message(id, "deleted", "deleted");
            Message y = deleteMessage(x);
            return new Gson().toJson(y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DELETE method for deleting an instance of Match with the given ID. If
     * the match doesn't exist, then don't delete anything. DELETE is idempotent, so
     * the result of sending the same command multiple times should be the same as
     * sending it exactly once.
     *
     * @param id the ID of the match to be deleted
     * @return null
     */
    @DELETE
    @Path("/match/{id1}/{id2}")
    @Produces("application/json")
    public String deleteMatch(
		@PathParam("id1") String id1,
		@PathParam("id2") String id2) 
	{
        try {
            Match x = new Match(id, "deleted", "deleted");
            Match y = deleteMatch(x);
            return new Gson().toJson(y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** DBMS Utility Functions *********************************************/
	
	// /retrieveStudent(String id), /retrieveStudents(), /retrieveMatches(String id), /retrieveMessages(String id1, String id2), /retrieveDates(String id1, String id2), /addOrUpdateStudent(Student student)
    // /addOrUpdateMatch(Match match), /addOrUpdateMessage(Message message), /addOrUpdateDate(Date date), /addNewMessage(Message message), /addNewDate(Date date), /deleteStudent(Student student), /deleteMatch(Match match)
    // /deleteMessage(Message message), /deleteDate(Date date)

    /**
     * Constants for a local Postgresql server with the monopoly database
     */
    private static final String DB_URI = "jdbc:postgresql://localhost:1853/cs262gDating";
    private static final String DB_LOGIN_ID = "postgres";
    private static final String DB_PASSWORD = "bjarne";
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
            rs = statement.executeQuery("SELECT * FROM Student WHERE CalvinID=" + id);
            if (rs.next()) {
                student = new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getLocalDate(10),
                        rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
                        rs.getString(16), rs.getString(17), rs.getBoolean(18), rs.getString(19), rs.getBoolean(20),
                        rs.getBoolean(21), rs.getBoolean(22), rs.getBoolean(23), rs.getBoolean(24), rs.getString(25),
                        rs.getInt(26), rs.getInt(27), rs.getString(28), rs.getString(29), rs.getInt(30), rs.getString(31),
                        rs.getString(32), rs.getBoolean(33), rs.getBoolean(34), rs.getInt(35), rs.getInt(36), rs.getInt(37),
                        rs.getInt(38), rs.getInt(39), rs.getInt(40), rs.getString(41));
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
            rs = statement.executeQuery("SELECT * FROM Student");
            while (rs.next()) {
                students.add(new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getLocalDate(10), rs.getString(11),
                        rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16),
                        rs.getString(17), rs.getBoolean(18), rs.getString(19), rs.getBoolean(20), rs.getBoolean(21),
                        rs.getBoolean(22), rs.getBoolean(23), rs.getBoolean(24), rs.getString(25), rs.getInt(26),
                        rs.getInt(27), rs.getString(28), rs.getString(29), rs.getInt(30), rs.getString(31),
                        rs.getString(32), rs.getBoolean(33), rs.getBoolean(34), rs.getInt(35), rs.getInt(36),
                        rs.getInt(37), rs.getInt(38), rs.getInt(39), rs.getInt(40), rs.getString(41)));
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
            rs = statement.executeQuery("SELECT * FROM Match WHERE (aCalvinID=" + id + " OR bCalvinID=" + id + ") AND (aValid=1 AND bValid=1)");
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
            rs = statement.executeQuery("SELECT * FROM Message WHERE (toID=" + id1 + " AND fromID=" + id2 +") OR (toID=" + id2 + " AND fromID=" + id1 +")");
            while (rs.next()) {
                messages.add(new Message(rs.getInt(1), rs.getTimestamp(2), rs.getString(3), rs.getString(4), rs.getString(5)));
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
    private List<Date> retrieveDates(String id1, String id2) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<Date> dates = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Date WHERE (aCalvinID=" + id1 + " AND bCalvinID=" + id2 +") OR aCalvinID=" + id2 + " AND bCalvinID=" + id1 +")");
            while (rs.next()) {
                dates.add(new Date(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getBoolean(5), rs.getString(6), rs.getString(7), rs.getTimestamp(8)));
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
            rs = statement.executeQuery("SELECT * FROM Student WHERE CalvinID=" + student.getCalvinID());
            if (rs.next()) {
                statement.executeUpdate("UPDATE Student SET password='" + student.getPassword() + "', picture=" + student.getPicture() + ", first='" + student.getFirst() + "', last='" + student.getLast() + "', username='" + student.getUsername() + "', collegeStartYear=" + student.getCollegeStartYear() + ", calvinGradYear=" + student.getCalvinGradYear() + ", calvinGradMonth='" + student.getCalvinGradMonth() + "', birthday=" + student.getBirthday() + ", homeCity='" + student.getHomeCity() + "', homeState='" + student.getHomeState() + "', homeCountry='" + student.getHomeCountry() + "', major='" + student.getMajor() + "', gender='" + student.getGender() + "', religion='" + student.getReligion() + "', mbti='" + student.getMbti() + "', hasJob=" + student.getHasJob() + ", job='" + student.getJob() + "', calvinT=" + student.getCalvinT() + ", calvinU=" + student.getCalvinU() + ", calvinL=" + student.getCalvinL() + ", calvinI=" + student.getCalvinI() + ", calvinP=" + student.getCalvinP() + ", hangout='" + student.getHangout() + "', hateHope=" + student.getHateHope() + ", bQuiv=" + student.getBQuiv() + ", diningPreference='" + student.getDiningPreference() + "', sports='" + student.getSports() + "', bunHate=" + student.getBunHate() + ", vocation='" + student.getVocation() + "', studySpot='" + student.getStudySpot() + "', chapelDay='" + student.getChapelDays() + "', loft='" + student.getLoft() + "', oceanO=" + student.getOceanO() + ", oceanC='" + student.getOceanC() + ", oceanE=" + student.getOceanE() + ", oceanA=" + student.getOceanA() + ", oceanN=" + student.getOceanN() + ", height=" + student.getHeight() + ", nationality='" + student.getNationality() + "' WHERE CalvinID=" + student.getCalvinID());
            } else {
                statement.executeUpdate("INSERT INTO Student VALUES ('" + student.getCalvinID() + "', '" + student.getPassword() + "', " + student.getPicture() + ", '" + student.getFirst() + "', '" + student.getLast() + "', '" + student.getUsername() + "', " + student.getCollegeStartYear() + ", " + student.getCalvinGradYear() + ", '" + student.getCalvinGradMonth() + "', " + student.getBirthday() + ", '" + student.getHomeCity() + "', '" + student.getHomeState() + "', '" + student.getHomeCountry() + "', '" + student.getMajor() + "', '" + student.getGender() + "', '" + student.getReligion() + "', '" + student.getMbti() + "', " + student.getHasJob() + ", '" + student.getJob() + "', " + student.getCalvinT() + ", " + student.getCalvinU() + ", " + student.getCalvinL() + ", " + student.getCalvinI() + ", " + student.getCalvinP() + ", '" + student.getHangout() + "', " + student.getHateHope() + ", " + student.getBQuiv() + ", '" + student.getDiningPreference() + "', '" + student.getSports() + "', " + student.getBunHate() + ", '" + student.getVocation() + "', '" + student.getStudySpot() + "', '" + student.getChapelDays() + "', '" + student.getLoft() + "', " + student.getOceanO() + ", '" + student.getOceanC() + ", " + student.getOceanE() + ", " + student.getOceanA() + ", " + student.getOceanN() + ", " + student.getHeight() + ", '" + student.getNationality() + "')");
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
            rs = statement.executeQuery("SELECT * FROM Match WHERE (aCalvinID=" + match.getACalvinID() + " AND bCalvinID=" + match.getBCalvinID() +") OR (aCalvinID=" + match.getBCalvinID() + " AND bCalvinID=" + match.getACalvinID() +")");
            if (rs.next()) {
                statement.executeUpdate("UPDATE Match SET aCalvinID='" + match.getACalvinID() + "', bCalvinID='" + match.getBCalvinID() + "', reason='" + match.getReason() + "', aValid=" + match.getAValid() + ", bValid=" + match.getBValid() + " WHERE (aCalvinID=" + match.getACalvinID() + " AND bCalvinID=" + match.getBCalvinID() +") OR (aCalvinID=" + match.getBCalvinID() + " AND bCalvinID=" + match.getACalvinID() +")");
            } else {
                statement.executeUpdate("INSERT INTO Match VALUES ('" + match.getACalvinID() + "', '" + match.getBCalvinID() + "', '" + match.getReason() + "', " + match.getAValid() + ", " + match.getBValid() + "')");
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
            rs = statement.executeQuery("SELECT * FROM Message WHERE ID=" + message.getID());
            if (rs.next()) {
                statement.executeUpdate("UPDATE Message SET timestamp=" + message.getTimestamp() + ", toID='" + message.getToID() + "', fromID='" + message.getFromID() + "', message='" + message.getMessage() + "' WHERE ID=" + message.getID());
            } else {
                statement.executeUpdate("INSERT INTO Message VALUES (" + message.getID() + ", " message.getTimestamp() + ", '" + message.getToID() + "', '" + message.getFromID() + "', '" + message.getMessage() + "')");
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
    private Date addOrUpdateDate(Date date) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Date WHERE ID=" + date.getID());
            if (rs.next()) {
                statement.executeUpdate("UPDATE Date SET aCalvinID='" + date.getACalvinID() + "', toID='" + date.getBCalvinID() + "', aAccept=" + date.getAAccept() + ", bAccept=" + date.getBAccept() + ", place='" + date.getPlace() + "', activity='" + date.getActivity() + "', timestamp=" + date.getTimestamp() + " WHERE ID=" + date.getID());
            } else {
                statement.executeUpdate("INSERT INTO Date VALUES (" + date.getID() + ", " date.getACalvinID() + "', '" + date.getBCalvinID() + "', " + date.getAAccept() + ", " + date.getBAccept() + ", '" + date.getPlace() + "', '" + date.getActivity() + "', " + date.getTimestamp() + "')");
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
            rs = statement.executeQuery("SELECT MAX(ID) FROM Message");
            if (rs.next()) {
                message.setId(rs.getInt(1) + 1);
            } else {
                throw new RuntimeException("failed to find unique ID...");
            }
            statement.executeUpdate("INSERT INTO Message VALUES (" + message.getID() + ", " message.getTimestamp() + ", '" + message.getToID() + "', '" + message.getFromID() + "', '" + message.getMessage() + "')");
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
    private Date addNewDate(Date date) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT MAX(ID) FROM Date");
            if (rs.next()) {
                date.setId(rs.getInt(1) + 1);
            } else {
                throw new RuntimeException("failed to find unique ID...");
            }
            statement.executeUpdate("INSERT INTO Date VALUES (" + date.getID() + ", " date.getACalvinID() + "', '" + date.getBCalvinID() + "', " + date.getAAccept() + ", " + date.getBAccept() + ", '" + date.getPlace() + "', '" + date.getActivity() + "', " + date.getTimestamp() + "')");
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
            statement.executeUpdate("DELETE FROM Student WHERE CalvinID='" + student.getCalvinID() + "'");
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
            statement.executeUpdate("DELETE FROM Match WHERE (aCalvinID='" + match.getACalvinID() + "' AND bCalvinID='" + match.getBCalvinID() + "') OR (aCalvinID='" + match.getBCalvinID() + "' AND bCalvinID='" + match.getACalvinID() + "')");
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
            statement.executeUpdate("DELETE FROM Message WHERE ID=" + message.getID());
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
    public Date deleteDate(Date date) throws Exception {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Date WHERE ID=" + date.getID());
        } catch (SQLException e) {
            throw (e);
        } finally {
            statement.close();
            connection.close();
        }
        return date;
    }


    /** Main *****************************************************/

    /**
     * Run this main method to fire up the service.
     *
     * @param args command-line arguments (ignored)
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:" + PORT + "/");
        server.start();

        System.out.println("Server running...");
        System.out.println("Web clients should visit: http://cs262.cs.calvin.edu/" + PORT + "/dating");
        System.out.println("Android emulators should visit: http://cs262.cs.calvin.edu/" + PORT + "/dating");
        System.out.println("Hit return to stop...");
        //noinspection ResultOfMethodCallIgnored
        System.in.read();
        System.out.println("Stopping server...");
        server.stop(0);
        System.out.println("Server stopped...");
    }
}