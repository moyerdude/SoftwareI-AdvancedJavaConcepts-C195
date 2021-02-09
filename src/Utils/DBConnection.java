package Utils;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;



/**  Handles the connection to the database.
 @author Stephen Moyer
 */
public class DBConnection {

    // JBDC URL parts
    private static final String protocal = "jdbc";
    private static final String venderName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ06BqL";
    //The complete URL
    private static final String jbdcURL = protocal + venderName + ipAddress;
    //Driver referance
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";

    public static Connection conn = null;

    private static final String userName = "U06BqL";

    private static final String password = "000000000";

    /** Starts the connection to the database.    */
    public static Connection startConnection(){

        try {
            Class.forName(MYSQLJDBCDriver);

            conn = (Connection)DriverManager.getConnection(jbdcURL,userName,password);

            System.out.println("Connection good");
        }
        catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
            System.out.println("Class Path not found");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL Exception");

        }

        return conn;
    }

    /** Ends the connection to the database.    */
    public static void endConnection() {

        try {
            conn.close();
            System.out.println("Connection Terminated");
        }

        catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL Exception on closing");

        }
    }

}
