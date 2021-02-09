package Controller;

import Models.Customers;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class controls the Modify Customer Screen.
 @author Stephen Moyer
 */
public class ModifyCustomerController implements Initializable {

    Stage stage;
    Parent scene;


    private ObservableList<String> COUNTRY = FXCollections.observableArrayList();
    private ObservableList<String>  STATE = FXCollections.observableArrayList();

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private ComboBox<String> state;

    @FXML
    private TextField zip;

    @FXML
    private TextField phone;

    @FXML
    private ComboBox<String> country;

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        System.out.println("The 'back' button was pressed");

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/CustomerList.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void populateFields(Customers customer) {

        id.setText(Integer.toString(customer.getCustomerID()));
        name.setText(customer.getCustomerName());
        address.setText(customer.getCustomerAddress());
        state.setValue(customer.getCustomerState());

        zip.setText(customer.getCustomerZip());
        phone.setText(customer.getCustomerPhone());

        country.setValue(Utils.State.setCountry(customer.getCustomerState()));
        country.setItems(Utils.State.getCountryText(COUNTRY));
        state.setItems(Utils.State.getSateList(STATE, country.getValue()));

    }

    @FXML
    void countryAction(ActionEvent event){

        state.getItems().clear();
        STATE = Utils.State.getSateList(STATE,country.getValue());
        state.setItems(STATE);

    }

    @FXML
    void saveAction(ActionEvent event) throws IOException {

        int customerid = Integer.parseInt(id.getText());
        String customerName = name.getText();
        String customerAddress = address.getText();
        String customerZip = zip.getText();
        String customerState = state.getValue();
        String customerPhone = phone.getText();
        if(!validateName(customerName)||!validateAddress(customerAddress)||!validateZip(customerZip)||!validatePhone(customerPhone)){

            System.out.println("is this getting to here");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid fields!");
            alert.setHeaderText("Invalid fields!");
            alert.setContentText("Please make sure all required fields are filled out correctly.");
            alert.showAndWait();;

        }
        else {

            Utils.CustomerDB.updateCustomer(customerid,customerName,customerAddress,customerZip,customerPhone,customerState);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/CustomerList.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }

    /** This method validates that there is a name in the Text Field.
     There needs to be a name in the text field.
     @param name This passes the name.
     @return true If there is a name typed.
     */
    public boolean validateName(String name) {
        if(name.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /** This method validates that there is a address typed.
     There must be a address typed and this makes sure there is.
     @param address passes the string in the text field.
     @return true If there is a address typed.
     */
    public boolean validateAddress(String address) {
        if(address.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /** This method validates that there is a zip code entered.
     There must be a zip typed and this makes sure there is.
     @param zip passes the string in the text field.
     @return true If there is a address typed.
     */
    public boolean validateZip(String zip) {
        if(zip.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /** This method validates that there is a phone code entered.
     There must be a phone typed and this makes sure there is.
     @param phone passes the string in the text field.
     @return true If there is a phone typed.
     */
    public boolean validatePhone(String phone) {
        if(phone.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


    /** This method initializes the modify customer screen.
     This makes sure all the items are set correctly on opening the screen.
     @param resourceBundle initialization bundle
     @param url The URL
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateFields(CustomerListController.SELECTEDCUSTOMER);

    }
}
