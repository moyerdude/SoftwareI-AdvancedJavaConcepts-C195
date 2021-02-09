package Controller;

import Utils.CustomerDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class controls the Add customer Screen.
 @author Stephen Moyer
 */
public class AddCustomerController implements Initializable {

    Stage stage;
    Parent scene;



    private ObservableList<String> STATES = FXCollections.observableArrayList();
    private ObservableList<String> CITY = FXCollections.observableArrayList();
    private ObservableList<String> CLEAR = FXCollections.observableArrayList();

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private ComboBox<String> country;

    @FXML
    private TextField zip;

    @FXML
    private TextField phone;

    @FXML
    private ComboBox<String> state;


    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        System.out.println("The 'back' button was pressed");

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AppointmentManager.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();


    }

    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

        String customerName = name.getText();
        String customerAddress = address.getText();
        String customerZip = zip.getText();
        String customerPhone = phone.getText();
        String customerState = state.getValue();

        if (!validateName(customerName) || !validateAddress(customerAddress) || !validateZip(customerZip) || !validatePhone(customerPhone)) {


            System.out.println("is this getting to here");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid fields!");
            alert.setHeaderText("Invalid fields!");
            alert.setContentText("Please make sure all required fields are filled out correctly.");
            alert.showAndWait();

        } else {

            CustomerDB.saveCustomer(customerName, customerAddress, customerZip, customerPhone, customerState);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/CustomerList.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }

    @FXML
    void setCountry(ActionEvent event) {

    }

    @FXML
    void countryAction(ActionEvent event){

        state.getItems().clear();
        CITY = Utils.State.getSateList(CITY,country.getValue());
        state.setItems(CITY);

    }

    /** This method validates that there is a name in the Text Field.
     There needs to be a name in the text field.
     @param name This passes the name.
     @return false If there is no name typed.
      */
    public boolean validateName(String name) {
        if (name.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /** This method validates that there is a address typed.
     There must be a address typed and this makes sure there is.
     @param address passes the string in the text field.
     @return false If there is no address typed.
      */
    public boolean validateAddress(String address) {
        if (address.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /** This method validates that there is a zip code entered.
     There must be a zip typed and this makes sure there is.
     @param zip passes the string in the text field.
     @return false If there is no address typed.
      */
    public boolean validateZip(String zip) {
        if (zip.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /** This method validates that there is a phone code entered.
     There must be a phone typed and this makes sure there is.
     @param phone passes the string in the text field.
     @return false If there is no phone typed.
     */
    public boolean validatePhone(String phone) {
        if (phone.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


    /** This method initializes the add customer screen.
     This makes sure all the items are set correctly on the openning the screen.
     @param resourceBundle initialization bundle
     @param url The URL
      */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        STATES = Utils.State.getCountryText(STATES);
        System.out.println(STATES);
        country.setItems(STATES);

    }
}


