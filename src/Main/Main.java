package Main;

import Utils.DBConnection;
import Utils.DBQuery;
import com.mysql.jdbc.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Statement;

/** This is the main controller the program.
  @author Stephen Moyer
 */
public class Main extends Application {

    /** This is the call to the first scene.
     Loads into the Mains screen.
     @param primaryStage Sets the primary stage.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../View/Main.fxml"));
        primaryStage.setTitle("Appointment Manager");
        primaryStage.setScene(new Scene(root, 500, 250));
        primaryStage.show();
    }

    /** Starts the program.
     Establishes the database connection and opens the first scene.
     @param args Args for the program.
      */
    public static void main(String[] args) throws SQLException {
        // Database connection
         Connection conn = DBConnection.startConnection();
         DBQuery.setStatement(conn);         // Create object
         Statement statement = DBQuery.getStatement();   //get statement

        launch(args);
        DBConnection.endConnection();



    }
}
