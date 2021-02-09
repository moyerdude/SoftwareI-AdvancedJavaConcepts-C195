package Controller;

import Models.Appointment;
import Models.Customers;
import Utils.AppointmentDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class controls the Appointment Manager screen.
 @author Stephen Moyer

 */
public class AppointmentManagerController implements Initializable {

    @FXML private Button signOutButton;

    Stage stage;
    Parent scene;
    public static Appointment SELECTEDAPPOINTMENT;
    private Appointment selectedApt;
    private boolean isMonthly;

    @FXML
    private Tab allView;

    @FXML
    private TableView <Appointment> allAppointmentsTable;

    @FXML
    private TableColumn<Appointment, Integer> allAppointmentID;

    @FXML
    private TableColumn<Appointment, String> allTitle;

    @FXML
    private TableColumn<Appointment, String> allDescription;

    @FXML
    private TableColumn<Appointment, String> allLocation;

    @FXML
    private TableColumn<Appointment, String> allContact;

    @FXML
    private TableColumn<Appointment, String> allType;

    @FXML
    private TableColumn<Appointment, String> allStartTime;

    @FXML
    private TableColumn<Appointment, String> allEndTime;

    @FXML
    private TableColumn<Customers, Integer> allCustomerID;


    @FXML
    private Tab weeklyView;

    @FXML
    private Button reportsButtons;

    @FXML
    private Tab MonthlyView;

    @FXML
    private Button addAppointment;

    @FXML
    private Button customerList;

    @FXML
    private Button addCustomer;

    @FXML
    private Button logger;

    @FXML
    private Button reportsButton;

    @FXML
    private TableView<Appointment> monthTable;

    @FXML
    private TableView<Appointment> wkTable;

    @FXML
    private TableColumn<Appointment, Integer> wkAppointmentID;

    @FXML
    private TableColumn<Appointment, String> wkTitle;

    @FXML
    private TableColumn<Appointment, String> wkDescription;

    @FXML
    private TableColumn<Appointment, String> wkLocation;

    @FXML
    private TableColumn<Appointment, String> wkContact;

    @FXML
    private TableColumn<Appointment, String> wkType;

    @FXML
    private TableColumn<Appointment, String> wkStartTime;

    @FXML
    private TableColumn<Appointment, String> wkEndTime;

    @FXML
    private TableColumn<Customers, Integer> wkCustomerID;

    @FXML
    private Tab monthlyView;

    @FXML
    private TableColumn<Appointment, Integer> monthAppointmentID;

    @FXML
    private TableColumn<Appointment, String> monthTitle;

    @FXML
    private TableColumn<Appointment, String> monthDescription;

    @FXML
    private TableColumn<Appointment, String> monthLocation;

    @FXML
    private TableColumn<Appointment, String> monthContact;

    @FXML
    private TableColumn<Appointment, String> monthType;

    @FXML
    private TableColumn<Appointment, String> monthStartTime;

    @FXML
    private TableColumn<Appointment, String> monthEndTime;

    @FXML
    private TableColumn<Customers, Integer> monthCustomerID;

    @FXML
    void monthlyViewChanger() {

        monthAppointmentID.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        monthTitle.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
        monthDescription.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        monthLocation.setCellValueFactory(new PropertyValueFactory<>("aptLocation"));
        monthType.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        monthStartTime.setCellValueFactory(new PropertyValueFactory<>("aptStart"));
        monthEndTime.setCellValueFactory(new PropertyValueFactory<>("aptEnd"));
        monthContact.setCellValueFactory(new PropertyValueFactory<>("aptContact"));
        monthCustomerID.setCellValueFactory(new PropertyValueFactory<>("aptCustomerId"));

        monthTable.setItems(AppointmentDB.getMonthlyAppointments());

    }

    @FXML
    void allViewChanger(){

        allAppointmentID.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        allTitle.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
        allDescription.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        allLocation.setCellValueFactory(new PropertyValueFactory<>("aptLocation"));
        allType.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        allStartTime.setCellValueFactory(new PropertyValueFactory<>("aptStart"));
        allEndTime.setCellValueFactory(new PropertyValueFactory<>("aptEnd"));
        allContact.setCellValueFactory(new PropertyValueFactory<>("aptContact"));
        allCustomerID.setCellValueFactory(new PropertyValueFactory<>("aptCustomerId"));

        allAppointmentsTable.setItems(AppointmentDB.getAllAppointments());

    }


    @FXML
    void weeklyViewChanger() {

        wkAppointmentID.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        wkTitle.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
        wkDescription.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        wkLocation.setCellValueFactory(new PropertyValueFactory<>("aptLocation"));
        wkType.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        wkStartTime.setCellValueFactory(new PropertyValueFactory<>("aptStart"));
        wkEndTime.setCellValueFactory(new PropertyValueFactory<>("aptEnd"));
        wkContact.setCellValueFactory(new PropertyValueFactory<>("aptContact"));
        wkCustomerID.setCellValueFactory(new PropertyValueFactory<>("aptCustomerId"));

        wkTable.setItems(AppointmentDB.getWeeklyAppointments());

    }

    //Opens the customer list screen
    @FXML
    void customerListAction(ActionEvent event) throws IOException {
        System.out.println("The 'Customer list' button was pressed");


        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/CustomerList.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void reportsButtonAction(ActionEvent event) throws IOException{

        System.out.println("The 'Reports' button was pressed");

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    @FXML
    void addCustomerAction(ActionEvent event) throws IOException {
        System.out.println("The 'Add Customer' button was pressed");

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    @FXML
    void signOutButtonAction(ActionEvent event) throws IOException {

        System.out.println("The 'Sign Out' button was pressed");

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    @FXML
    void addAppointmentAction(ActionEvent event) throws IOException {

        System.out.println("The 'Add Appointment' button was pressed");

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void deleteAppointmentAction(ActionEvent event){

        System.out.println("does this get 1?");
        Appointment apt = null;
        if(monthlyView.isSelected()) {
            System.out.println("does this get 2?");
            isMonthly = true;
            if(monthTable.getSelectionModel().getSelectedItem() != null) {
                selectedApt = monthTable.getSelectionModel().getSelectedItem();
                apt = monthTable.getSelectionModel().getSelectedItem();
            } else {
                return;
            }
        }
        else if(allView.isSelected()){
            System.out.println("does this get 2?");
            isMonthly = true;
            if(allAppointmentsTable.getSelectionModel().getSelectedItem() != null) {
                selectedApt = allAppointmentsTable.getSelectionModel().getSelectedItem();
                apt = allAppointmentsTable.getSelectionModel().getSelectedItem();
            } else {
                return;
            }

        }
        else {
            isMonthly = false;
            System.out.println("does this get 3?");
            if(wkTable.getSelectionModel().getSelectedItem() != null) {
                System.out.println("does this get 4?");
                selectedApt = wkTable.getSelectionModel().getSelectedItem();

                System.out.println("does this get 45");
            } else {

                return;
            }
        }
        System.out.println("does this get 6");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Appointment Record");
        alert.setContentText("Confirm you want to delete Appointment with ID: " + selectedApt.getAptId() + " Appointment Type: " + selectedApt.getAptType() );
        alert.showAndWait().ifPresent((response -> {
            if(response == ButtonType.OK) {
                AppointmentDB.deleteAppointment(selectedApt.getAptId());
                if(isMonthly) {
                    monthTable.setItems(AppointmentDB.getMonthlyAppointments());
                    allAppointmentsTable.setItems(AppointmentDB.getAllAppointments());
                } else {
                    wkTable.setItems(AppointmentDB.getWeeklyAppointments());
                }
            }
        }));


    }

    @FXML
    void modifyAppointmentAction(ActionEvent event) throws IOException {
       // boolean temp = true;
        System.out.println("The 'Modify Appointment' button was pressed");

        if (wkTable.getSelectionModel().getSelectedItem() != null)  {
            selectedApt = wkTable.getSelectionModel().getSelectedItem();

            SELECTEDAPPOINTMENT = selectedApt;

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/ModifyAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
        else if(monthTable.getSelectionModel().getSelectedItem() != null){
            selectedApt = monthTable.getSelectionModel().getSelectedItem();

            SELECTEDAPPOINTMENT = selectedApt;

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/ModifyAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
        else if(allAppointmentsTable.getSelectionModel().getSelectedItem() != null){
            selectedApt = allAppointmentsTable.getSelectionModel().getSelectedItem();

            SELECTEDAPPOINTMENT = selectedApt;

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/ModifyAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();



        }

        else {

            System.out.println("is this getting to here");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment not selected");
            alert.setHeaderText("Appointment not selected");
            alert.setContentText("Appointment not selected. Please select an Appointment to modify.");
            alert.showAndWait();

            return;
        }

    }

    @FXML
    private void logsButton() {

    }

    /** This method handles the logger button.
     This method opens the activity logger.
     @param event Loads the event.
     */
    private void loggerPushed(ActionEvent event){
           File file = new File("login_activity.txt");
              if(file.exists()) {
              if(Desktop.isDesktopSupported()) {
                    try {
                       Desktop.getDesktop().open(file);
                       }
                    catch (IOException e) {
                        System.out.println("Error Opening Log File: " + e.getMessage());
                   }
               }
           }

    }


    /** This method initializes the appointment manager screen.
     This makes sure all the items are set correctly on opening the screen.
     One of my Lambda expressions in used in this method. Setting the event handler.
     @param resourceBundle initialization bundle
     @param url The URL
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        System.out.println("Error before this?");

        wkAppointmentID.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        wkTitle.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
        wkDescription.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        wkLocation.setCellValueFactory(new PropertyValueFactory<>("aptLocation"));
        wkType.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        wkStartTime.setCellValueFactory(new PropertyValueFactory<>("aptStart"));
        wkEndTime.setCellValueFactory(new PropertyValueFactory<>("aptEnd"));
        wkContact.setCellValueFactory(new PropertyValueFactory<>("aptContact"));
        wkCustomerID.setCellValueFactory(new PropertyValueFactory<>("aptCustomerId"));

        wkTable.setItems(AppointmentDB.getWeeklyAppointments());

        logger.setOnAction(event -> loggerPushed(event));
    }

}
