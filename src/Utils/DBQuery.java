package Utils;

import Models.Customers;
import Models.Users;
import com.mysql.jdbc.Connection;
import com.sun.javafx.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Handles the database queries.
 @author Stephen Moyer
 */
public class DBQuery {

    private final static Connection conn = DBConnection.conn;

    private static Statement statement;


    public static void setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }

    public static Statement getStatement(){
        return statement;
    }

    private static Users currentUser;

    public static Users getCurrentUser() {
        return currentUser;
    }

    /** Checks if the login informations in valid.
     @param username Passes the username.
     @param password Passes the password.
     */
    public static Boolean login(String username, String password) {
        try {

            String query = "SELECT * FROM users WHERE User_Name='" + username + "' AND Password='" + password + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                currentUser = new Users();
                currentUser.setUsername(results.getString("User_Name"));
                return true;
            }
            else {
                System.out.println("The Else loop was taken");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

}
