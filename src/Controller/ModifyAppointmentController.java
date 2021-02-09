package Controller;

import Models.Appointment;
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

/** This class controls the Modify Appointment screen.
 @author Stephen Moyer
 */
public class ModifyAppointmentController implements Initializable {

    private ObservableList<String> contact = FXCollections.observableArrayList();
    private ObservableList<String> timesAgain = FXCollections.observableArrayList();

    Stage stage;
    Parent scene;

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

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AppointmentManager.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

        boolean fullFields = true;
        boolean overlapping = true;

        int ID = Integer.parseInt(aptID.getText());
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

        if(!validateContact(appointmentContact)||!validateType(appointmentType)||!validateTimeV2(startTime)||!validateDate(ld) || !validateDate(le) || !validateTimeV2(endTime)) {
            System.out.println("Oops! Missing fields.");
            fullFields = false;
            System.out.println("Did this get here 2");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Fields");
            alert.setHeaderText("Missing Fields");
            alert.setContentText("There are missing fields that are required.");
            alert.showAndWait();

        }
        if(Utils.AppointmentDB.overlappingAppointment(ID, customerID, ld.toString(), startTime,endTime)) {

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
            Utils.AppointmentDB.updateAppointment(ID,customerID, startDate,startTime , endDate, endTime,title, description, location, contact ,type);
            System.out.println("Something should be saved");

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/AppointmentManager.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
        else {

            System.out.println("Skipped everything....");

        }
    }

    @FXML
    void aptStartDateAction(){

        aptEndDate.setText(aptStartDate.getValue().toString());
    }


    /** This method populates all the fields with the selected appointment.
        Puts the correct information into all the combo boxs and textfields.
     @param appointment Passed the selected appointment
      */
    public void populateAppointmentFields(Appointment appointment) {
        int temp = appointment.getAptId();
        int temp1 = appointment.getAptCustomerId();

        aptID.setText(String.valueOf(temp));
        aptTitle.setText(appointment.getAptTitle());
        aptDescription.setText(appointment.getAptDescription());
        aptLocation.setText(appointment.getAptLocation());
        aptType.setText(appointment.getAptType());
        aptContact.setValue(appointment.getAptContact());
        aptStartDate.setValue(appointment.getStartDateOnly());
        aptEndDate.setText(appointment.getEndDateOnly().toString());
        aptStartTime.setValue(Utils.AppointmentDB.convertToLocal(appointment.getStartTimeOnly()));
        aptEndTime.setValue(Utils.AppointmentDB.convertToLocal(appointment.getEndTimeOnly()));
        aptCustomerID.setText(String.valueOf(temp1));

        timesAgain = Utils.AppointmentDB.getLocalTimes(timesAgain);

        aptStartTime.setItems(timesAgain);
        aptEndTime.setItems(timesAgain);

        aptContact.setItems(Utils.AppointmentDB.getContactList(contact));
        startTimeLocal.setText(Utils.AppointmentDB.convertToEST(Utils.AppointmentDB.convertToLocal(appointment.getStartTimeOnly())));
        endTimeLocal.setText(Utils.AppointmentDB.convertToEST(Utils.AppointmentDB.convertToLocal(appointment.getEndTimeOnly())));
    }

    /**This method validates that there is a contact.
     This makes sure that the Text Field has text before save.
     @param appointmentContact Sends contact
     @return true if there is selected text
     */
    public boolean validateContact(int appointmentContact) {
        if(appointmentContact == -1) {

            System.out.println("A Contact must be selected");
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
            System.out.println("A Type Time must be selected");
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

            System.out.println("An Appointment Date must be selected");
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

    /** This method validates that there is a time selected.
     This validates text is in the combobox for time.
     @param ap This is a time
     @return true True if there is a selection
     */
    public boolean validateTimeV2(String ap) {
        if(ap == null || ap.trim().isEmpty()) {

            System.out.println("A Type Time must be selected V2");
            return false;
        } else {
            return true;
        }
    }

    /** This method initializes the modify appointment screen.
     This makes sure all the items are set correctly the modify appointment screen.
     @param rb initialization bundle
     @param url The URL
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aptUserName.setText(Models.Users.getUsername());
        populateAppointmentFields(AppointmentManagerController.SELECTEDAPPOINTMENT);

    }

}
