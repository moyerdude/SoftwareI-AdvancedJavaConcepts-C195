package Controller;

import Utils.DBQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.ResourceBundle;

/** This class controls the report screen.  */
public class ReportsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button backButton;

    @FXML
    private TextArea reportTwo;

    @FXML
    private TextArea reportOne;

    @FXML
    private TextArea reportThree;

    /**
     * This displays the information for the report one.
     * Handles the query and displays the information for the first report.
     */
    public void handleReportOne() {
        try {
            Statement statement = DBQuery.getStatement();
            String query = "SELECT customers.Customer_Name, appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Type, appointments.Start, appointments.End, appointments.contact_ID FROM WJ06BqL.appointments JOIN WJ06BqL.customers ON customers.Customer_ID = appointments.Customer_ID GROUP BY customers.Customer_ID,appointments.Appointment_ID,appointments.Title, appointments.Description,appointments.Type, appointments.Start,appointments.End, appointments.Customer_ID";
            ResultSet results = statement.executeQuery(query);
            StringBuilder reportTwoText = new StringBuilder();
            reportTwoText.append(String.format("%1$-25s %2$-25s %3$-25s %4$-25s %5$-25s %6$-25s %7$-25s %8$s \n",
                    "Customer", "Appointment ID", "Title", "Description", "Type", "Start", "End", "Customer ID"));
            reportTwoText.append(String.join("", Collections.nCopies(170, "-")));
            reportTwoText.append("\n");
            while (results.next()) {

                String startTS = results.getString("Start");
                String endTS = results.getString("End");

                String start = Utils.AppointmentDB.displayLocalTime(startTS);
                String end = Utils.AppointmentDB.displayLocalTime(endTS);

                reportTwoText.append(String.format("%1$-25s %2$-25s %3$-25s %4$-25s %5$-25s %6$-25s %7$-25s %8$s \n",
                        results.getString("Customer_Name"),
                        results.getString("Appointment_ID"),
                        results.getString("Title"),
                        results.getString("Description"),
                        results.getString("Type"),
                        start,
                        end,
                        results.getString("Contact_ID")));
            }

            reportOne.setText(reportTwoText.toString());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    /**
     * This displays the information for the report two.
     * Handles the query and displays the information for the second report.
     */
    public void handleReportTwo() {
        try {
            Statement statement = DBQuery.getStatement();
            String query = "SELECT contacts.Contact_Name, appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID FROM WJ06BqL.appointments JOIN WJ06BqL.contacts ON contacts.Contact_ID = appointments.Contact_ID GROUP BY contacts.Contact_ID,appointments.Appointment_ID,appointments.Title, appointments.Description,appointments.Type, appointments.Start,appointments.End, appointments.Customer_ID";
            ResultSet results = statement.executeQuery(query);
            StringBuilder reportTwoText = new StringBuilder();
            reportTwoText.append(String.format("%1$-25s %2$-25s %3$-25s %4$-25s %5$-25s %6$-25s %7$-25s %8$s \n",
                    "Contact", "Appointment ID", "Title", "Description", "Type", "Start", "End", "Customer ID"));
            reportTwoText.append(String.join("", Collections.nCopies(170, "-")));
            reportTwoText.append("\n");
            while (results.next()) {

                String startTS = results.getString("Start");
                String endTS = results.getString("End");
            //    convertToLT(start);
            //    convertToLT(end);
                String start = Utils.AppointmentDB.displayLocalTime(startTS);
                String end = Utils.AppointmentDB.displayLocalTime(endTS);
                System.out.println("This is the start: " + start);
                System.out.println("This is the end: " + end);
                reportTwoText.append(String.format("%1$-25s %2$-25s %3$-25s %4$-25s %5$-25s %6$-25s %7$-25s %8$s \n",
                        results.getString("Contact_Name"),
                        results.getString("Appointment_ID"),
                        results.getString("Title"),
                        results.getString("Description"),
                        results.getString("Type"),
                        start,
                        end,
                        results.getString("Customer_ID")));


            }

            reportTwo.setText(reportTwoText.toString());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
//  Utils.AppointmentDB.displayLocalTime(

    /**
     * This displays the information for the report three.
     * Handles the query and displays the information for the third report.
     */
    public void handleReportThree() {
        try {
            Statement statement = DBQuery.getStatement();
            String query = "SELECT COUNT(*) as 'Total', MONTH(appointments.Start), appointments.Type FROM WJ06BqL.appointments GROUP BY MONTH(appointments.Start), appointments.Type";
            ResultSet results = statement.executeQuery(query);
            StringBuilder reportThreeText = new StringBuilder();
            reportThreeText.append(String.format("%1$-45s %2$-65s %3$-55s \n", "Total Appointments", "Appointments Month", "Appointment Type"));
            reportThreeText.append(String.join("", Collections.nCopies(110, "-")));
            reportThreeText.append("\n");
            while (results.next()) {
                reportThreeText.append(String.format("%1$-65s %2$-65s %3$-65s \n",
                        results.getInt("Total"),
                        results.getString("MONTH(appointments.Start)"),
                        results.getString("Type")));
            }

            reportThree.setText(reportThreeText.toString());
        } catch (SQLException e) {
            System.out.println("SQLExcpetion: " + e.getMessage());
        }

    }

    @FXML
    void handleBackButton(ActionEvent event) throws IOException {

        System.out.println("The 'Customer list' button was pressed");

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AppointmentManager.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();


    }

    /**
     * This method initializes the reports screen.
     * This makes sure all the items are set correctly on opening the screen.
     *
     * @param rb  initialization bundle
     * @param url The URL
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleReportOne();
        handleReportTwo();
        handleReportThree();
    }

    public static String convertToLT(String time) {


        ZoneId x = ZoneId.systemDefault();

        time = time.substring(0, time.length() - 5);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        LocalDateTime ldt = LocalDateTime.parse(time, df);

        ZonedDateTime zdt = ldt.atZone((ZoneId.of(ZoneId.systemDefault().toString())));
        System.out.println(zdt);
        ZonedDateTime utcDate = zdt.withZoneSameInstant(ZoneId.of("America/New_York"));
        ldt = utcDate.toLocalDateTime();
        Timestamp ts = Timestamp.valueOf(ldt);

        //Timestamp ts = Timestamp.valueOf(ldt);

        return ts.toString();


    }



}
