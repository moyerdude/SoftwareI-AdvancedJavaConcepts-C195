package Controller;

import Models.Appointment;
import Models.Logger;
import Utils.AppointmentDB;
import Utils.DBQuery;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/** This class controls the main login screen.
 @author Stephen Moyer
 */
public class MainController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Label zoneID;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label mainMessage;

    @FXML
    private Label languageMessage;

    @FXML
    private Button signInButton;

    @FXML
    private ToggleGroup selectLanguage;

    @FXML
    private Button exitButton;

    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    @FXML
    private RadioButton englishRadioButton;

    @FXML
    private RadioButton frenchRadioButton;

    private String errorHeader;
    private String errorTitle;
    private String errorText;


    //Sign in to and open to the main page
    @FXML
    void signInButtonAction(ActionEvent event) {

        try {

            String user = userName.getText();
            String pass = password.getText();

            boolean validUser = DBQuery.login(user, pass);

            if (validUser) {

                System.out.println("The 'Sign In' button was pressed");

                Logger.log(user, true);

                Appointment appointment = AppointmentDB.appointmentIn15Min();
                if (appointment != null) {

                    int id = appointment.getAptId();
                    String time = appointment.getStartTimeOnly();
                    String date = appointment.getStartDateOnly().toString();

                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
                    String aptTime = date + " " + time;
                    LocalDateTime ldt = LocalDateTime.parse(aptTime, df);
                    ZonedDateTime zdt = ldt.atZone((ZoneId.of("UTC")));
                    ZonedDateTime utcDate = zdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
                    ldt = utcDate.toLocalDateTime();
                    Timestamp ts = Timestamp.valueOf(ldt);
                    aptTime = ts.toString();

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Upcoming Appointment");
                    alert.setHeaderText("You have an upcoming Appointment");
                    alert.setContentText("Appointment details: Appointment ID: " + id + " Start Time: " + aptTime);
                    alert.showAndWait();

                    appointment.getAptId();

                } else if (appointment == null) {
                    System.out.println("is this getting to here");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("No Upcoming Appointments");
                    alert.setHeaderText("No Upcoming Appointment");
                    alert.setContentText("You have no upcoming appointments");
                    alert.showAndWait();

                }

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/AppointmentManager.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            } else {
                Logger.log(user, false);

                System.out.println("is this getting to here");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(errorTitle);
                alert.setHeaderText(errorHeader);
                alert.setContentText(errorText);
                alert.showAndWait();

            }
        }
        catch (IOException e) {
            System.out.println("Customer Main Error: " + e.getMessage());

        }

    }

    /** This method is handles the Exit program button.
     This makes sure that that when the exit button is pressed the program closes.
     @param event Handles event.
      */
    private void exitButtonPushed(ActionEvent event){

        System.out.println("The 'Cancel' button was pressed");
        Platform.exit();

    }

    @FXML
    void exitButtonAction(ActionEvent event) {

    }

    /** This method initializes the Login screen.
     This makes sure all the items are set correctly on opening the screen.
     One of my lambda expressions is used in this class. Sets the event handler.
     @param rb initialization bundle
     @param url The URL
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();

        rb = ResourceBundle.getBundle("languages/login", locale);

        exitButton.setOnAction(event -> exitButtonPushed(event));

        zoneID.setText(ZoneId.systemDefault().toString());
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        signInButton.setText(rb.getString("login"));
        mainMessage.setText(rb.getString("message"));
        languageMessage.setText(rb.getString("language"));
        exitButton.setText(rb.getString("exit"));
        errorHeader = rb.getString("errorheader");
        errorHeader = rb.getString("errorheader");
        errorTitle = rb.getString("errortitle");
        errorText = rb.getString("errortext");

    }

}
