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

//Get Students, ~make Students~, update Students, delete Students (deletes associated Matches but not Messages unless both are deleted) get Matches, ~make Matches~, update Matches, get Messages, make Messages, update Messages, delete Messages (if both Students do not exist), get Date, make Date, update Date, delete Date

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
     * GET method that returns a list of Matches for a student based on a Student id
     *
     * @param id a student id in the dating database
     * @return a JSON version of a list of Matches, if any, for the given id.
     */
    @GET
    @Path("/matches/{id}")
    @Produces("application/json")
    public String getStudent(@PathParam("id") String id) {
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
     * @param id the ID for the new student, assumed to be unique
     * @param studentLine a JSON representation of the student; the id parameter overrides any id in this line
     * @return JSON representation of the updated student, or NULL for errors
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
     * @param id the ID of the player to be deleted
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
	
	//retrieveStudent(String id), retrieveMatches(String id), retrieveMessages(String id1, String id2), retrieveDates(String id1, String id2), addOrUpdateStudent(Student student), addOrUpdateMatch(Match match), addOrUpdateMessage(Message message), addOrUpdateDate(Date date), addNewMessage(Message message), addNewDate(Date date), deleteStudent(Student student), deleteMatch(Match match), deleteMessage(Message message), deleteDate(Date date)

    /**
     * Constants for a local Postgresql server with the monopoly database
     */
    private static final String DB_URI = "jdbc:postgresql://localhost:1853/cs262gDating";
    private static final String DB_LOGIN_ID = "postgres";
    private static final String DB_PASSWORD = "bjarne";
    private static final String PORT = "8087";

    /*
     * Utility method that does the database query, potentially throwing an SQLException,
     * returning a player object (or null).
     */
    private Player retrievePlayer(int id) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        Player player = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Player WHERE id=" + id);
            if (rs.next()) {
                player = new Player(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            throw (e);
        } finally {
            rs.close();
            statement.close();
            connection.close();
        }
        return player;
    }

    /*
    * Utility method that does the database query, potentially throwing an SQLException,
    * returning a list of name-value map objects (potentially empty).
    */
    private List<Player> retrievePlayers() throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<Player> players = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Player");
            while (rs.next()) {
                players.add(new Player(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            throw (e);
        } finally {
            rs.close();
            statement.close();
            connection.close();
        }
        return players;
    }

    /*
    * Utility method that does the database update, potentially throwing an SQLException,
    * returning the player, potentially new.
    */
    private Player addOrUpdatePlayer(Player player) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Player WHERE id=" + player.getId());
            if (rs.next()) {
                statement.executeUpdate("UPDATE Player SET emailaddress='" + player.getEmailaddress() + "', name='" + player.getName() + "' WHERE id=" + player.getId());
            } else {
                statement.executeUpdate("INSERT INTO Player VALUES (" + player.getId() + ", '" + player.getEmailaddress() + "', '" + player.getName() + "')");
            }
        } catch (SQLException e) {
            throw (e);
        } finally {
            rs.close();
            statement.close();
            connection.close();
        }
        return player;
    }

    /*
    * Utility method that adds the given player using a new,unique ID, potentially throwing an SQLException,
    * returning the new player
    */
    private Player addNewPlayer(Player player) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT MAX(ID) FROM Player");
            if (rs.next()) {
                player.setId(rs.getInt(1) + 1);
            } else {
                throw new RuntimeException("failed to find unique ID...");
            }
            statement.executeUpdate("INSERT INTO Player VALUES (" + player.getId() + ", '" + player.getEmailaddress() + "', '" + player.getName() + "')");
        } catch (SQLException e) {
            throw (e);
        } finally {
            rs.close();
            statement.close();
            connection.close();
        }
        return player;
    }

    /*
    * Utility method that does the database update, potentially throwing an SQLException,
    * returning the player, potentially new.
    */
    public Player deletePlayer(Player player) throws Exception {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Player WHERE id=" + player.getId());
        } catch (SQLException e) {
            throw (e);
        } finally {
            statement.close();
            connection.close();
        }
        return player;
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
        System.out.println("Web clients should visit: http://localhost:" + PORT + "/monopoly");
        System.out.println("Android emulators should visit: http://LOCAL_IP_ADDRESS:" + PORT + "/monopoly");
        System.out.println("Hit return to stop...");
        //noinspection ResultOfMethodCallIgnored
        System.in.read();
        System.out.println("Stopping server...");
        server.stop(0);
        System.out.println("Server stopped...");
    }
}