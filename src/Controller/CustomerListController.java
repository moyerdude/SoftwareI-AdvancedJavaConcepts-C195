package Controller;

import Models.Customers;
import Utils.CustomerDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class controls the Customer List Screen.
 @author Stephen Moyer
 */
public class CustomerListController implements Initializable {


    Stage stage;
    Parent scene;

    @FXML
    private Button addCustomer;

    @FXML
    private TableColumn<Customers, String> customerState;

    @FXML
    private TableView<Customers> customerTable;

    @FXML
    private TableColumn<Customers, String> customerCountry;

    @FXML
    private TableColumn<Customers, Integer> customerID;

    @FXML
    private TableColumn<Customers, String> customerName;

    @FXML
    private TableColumn<Customers, String> customerAddress;

    @FXML
    private TableColumn<Customers, String> customerZip;

    @FXML
    private TableColumn<Customers, String> customerPhone;

    private Customers selectedCustomer;

    public static Customers SELECTEDCUSTOMER;

    @FXML
    void addCustomerAction(ActionEvent event) throws IOException {
        System.out.println("The 'Add Customer' button was pressed");

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        System.out.println("The 'back' button was pressed");

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AppointmentManager.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void modifyCustomerAction(ActionEvent event) throws IOException {

        System.out.println("The 'Modify Customer' button was pressed");


        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

            SELECTEDCUSTOMER = selectedCustomer;

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/ModifyCustomer.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
        else {

            System.out.println("is this getting to here");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer not selected");
            alert.setHeaderText("Customer not selected");
            alert.setContentText("Customer not selected. Please select a customer to modify.");
            alert.showAndWait();

            return;
        }

    }

    @FXML
    void deleteCustomerAction(ActionEvent event) {

        System.out.println("The 'Delete Customer' button was pressed");

        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        } else {

            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Customer Record");
        alert.setContentText("Delete Customer: " + selectedCustomer.getCustomerName() + "?\nPlease ");
        alert.showAndWait().ifPresent((response -> {
            if (response == ButtonType.OK) {
                CustomerDB.deleteCustomer(selectedCustomer.getCustomerID());
                customerTable.setItems(CustomerDB.getAllCustomers());
            }
        }));

    }

    /** This method initializes the customer list screen.
     This makes sure all the items are set correctly on opening the screen.
     @param rb initialization bundle
     @param url The URL
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerZip.setCellValueFactory(new PropertyValueFactory<>("customerZip"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customerState.setCellValueFactory(new PropertyValueFactory<>("customerState"));
        customerCountry.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        customerTable.setItems(CustomerDB.getAllCustomers());
    }
}


