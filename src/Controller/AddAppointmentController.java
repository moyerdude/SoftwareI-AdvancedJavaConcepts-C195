package Controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

/** This Class controls the Add Appointment Screen.
 @author Stephen Moyer
 */
public class AddAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    private ObservableList<String> times = FXCollections.observableArrayList();
    private ObservableList<String> timesAgain = FXCollections.observableArrayList();
    private ObservableList<String>  contact = FXCollections.observableArrayList();

    @FXML
    private TextField startTimeLocal;

    @FXML
    private TextField endTimeLocal;

    @FXML
    private TextField aptUserName;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;

    @FXML
    private DatePicker aptStartDate;

    @FXML
    private TextField aptID;

    @FXML
    private TextField aptTitle;

    @FXML
    private TextField aptLocation;

    @FXML
    private ComboBox<String> aptContact;

    @FXML
    private TextField aptType;

    @FXML
    private TextField aptEndDate;

    @FXML
    private TextField aptCustomerID;

    @FXML
    private TextArea aptDescription;

    @FXML
    private ComboBox<String> aptStartTime;

    @FXML
    private ComboBox<String> aptEndTime;

    @FXML
    void startTimeAction(ActionEvent event){
        startTimeLocal.setText(Utils.AppointmentDB.convertToEST(aptStartTime.getValue()));
    }

    @FXML
    void endTimeAction(ActionEvent event){
        endTimeLocal.setText(Utils.AppointmentDB.convertToEST(aptEndTime.getValue()));
    }

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {

        System.out.println("The 'Back' button was pressed");

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AppointmentManager.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void aptStartDateAction(){

        aptEndDate.setText(String.valueOf(aptStartDate.getValue()));
    }

    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

        boolean fullFields = true;
        boolean overlapping = true;

        String customerID = aptCustomerID.getText();
        String startDate = aptStartDate.getValue().toString();
        String startTime = aptStartTime.getValue();
        String endDate = aptEndDate.getText();
        String endTime = aptEndTime.getValue();
        String title = aptTitle.getText();
        String description = aptDescription.getText();
        String location = aptLocation.getText();
        String contact = aptContact.getValue();
        String type = aptType.getText();


        int appointmentContact = aptContact.getSelectionModel().getSelectedIndex();
        String appointmentType = aptType.getText();
        int aptTime = aptStartTime.getSelectionModel().getSelectedIndex();
        int aptTimeEnd = aptEndTime.getSelectionModel().getSelectedIndex();
        LocalDate ld = aptStartDate.getValue();
        LocalDate le = LocalDate.parse(endDate);

        if(!validateContact(appointmentContact)||!validateType(appointmentType)||!validateTime(aptTime)||!validateDate(ld) || !validateDate(le) || !validateTime(aptTimeEnd)) {
            System.out.println("Oops! Missing fields.");
            fullFields = false;
            System.out.println("Did this get here 2");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Fields");
            alert.setHeaderText("Missing Fields");
            alert.setContentText("There are missing fields that are required.");
            alert.showAndWait();

        }
        if(Utils.AppointmentDB.overlappingAppointment(-1, customerID, ld.toString(), timesAgain.get(aptTime), timesAgain.get(aptTimeEnd))) {

            System.out.println("Overlapping appointment");
            overlapping = false;
            System.out.println("Did this get here 3");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Overlapping Appointment");
            alert.setHeaderText("Overlapping Appointment");
            alert.setContentText("Customer " + customerID + " already has an appointment at that time.");
            alert.showAndWait();

        }

        if (validateEndTime(startDate, startTime, endDate, endTime) == false){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Impossible Time");
            alert.setHeaderText("Impossible Time");
            alert.setContentText("Appointment end time cannot be before start time");
            alert.showAndWait();

        }

        boolean weekend = Utils.AppointmentDB.noWeekEnds(startDate,endDate,false);
        if(weekend == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Weekend Time");
            alert.setHeaderText("Weekend Selected");
            alert.setContentText("You are not allowed to schedule an appointment during the weekend.");
            alert.showAndWait();

        }

        if(overlapping == true && fullFields == true && validateEndTime(startDate, startTime, endDate, endTime) == true && weekend == false) {
            Utils.AppointmentDB.saveAppointment(customerID, startDate,startTime , endDate, endTime,title, description, location, contact ,type);
            System.out.println("Something should be saved");

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/AppointmentManager.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } else {

            System.out.println("Skipped everything....");

        }

    }


    /**This method validates that there is a contact.
     This makes sure that the Text Field has text before save.
     @param appointmentContact Sends contact
     @return true Returns true of there is text
      */
    public boolean validateContact(int appointmentContact) {
        if(appointmentContact == -1) {

            return false;
        } else {
            return true;
        }
    }


    /** This method validates type has test.
     This validates text is in the text  field.
     @param appointmentType This passes the type
     @return true True if there is text
     */
    public boolean validateType(String appointmentType) {

        if(appointmentType == null || appointmentType.trim().isEmpty()) {

            return false;
        } else {
            return true;
        }
    }

    /** This method validates that there is a time selected.
     This validates text is in the combobox for time.
     @param aptTime This is a time
     @return true True if there is a selection
     */
    public boolean validateTime(int aptTime) {
        if(aptTime == -1) {

            return false;
        } else {
            return true;
        }
    }

    /** This method validates that there is a date selected.
     Makes sure that the date is selected and not blank.
     @param aptDate Passes the selected date.
     @return true True if there is a selection.
     */
    public boolean validateDate(LocalDate aptDate) {
        if(aptDate == null) {

            return false;
        } else {
            return true;
        }
    }


    /** This method validates that time is correct.
     This method makes sure that the end time is after the start time.
     @param ed This passes the end date.
     @param et This passed the end time.
     @param st This passes the start time.
     @param et This passes the end time.
     @return true True if the time is valid.
      */
    public boolean validateEndTime(String sd,String st, String ed, String et){

        LocalDate ED = LocalDate.parse(ed);
        LocalDate SD = LocalDate.parse(sd);
        LocalTime ST = LocalTime.parse(st);
        LocalTime ET = LocalTime.parse(et);


        if(ET.isAfter(ST) && (ED.isAfter(SD) || ED.isEqual(SD))){
            return true;
        }
        else{
            return false;
        }

    }


    /** This method initializes the Add Appointment screen.
     Makes sure that all the correct comboboxs and text fields are completed.
      */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        times = Utils.AppointmentDB.getLocalTimes(timesAgain);
        aptStartTime.setItems(times);
        aptEndTime.setItems(times);
        aptUserName.setText(Models.Users.getUsername());
        aptContact.setItems(Utils.AppointmentDB.getContactList(contact));

    }



}
