package Utils;

import Models.Customers;
import com.mysql.jdbc.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** This handles the customer updates to the Database.
 @author Stephen Moyer
 */
public class CustomerDB {

    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    private static Statement statement;

    /** Sets the connection Statement.
     @param conn This is the connection variable.
      */
    public static void setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }

    /** Gets the connection Statement.   */
    public static Statement getStatement(){
        return statement;
    }

    private static Customers currentCustomer;

    /** Gets the current customer.
     @return currentCustomer Returns the current Customer.
     */
    public static Customers getCurrentCustomer() {
        return currentCustomer;
    }

    /** Get All the current customers in the database.      */
    public static ObservableList<Customers> getAllCustomers() {
        allCustomers.clear();
        try {

            Statement statement = DBQuery.getStatement();
            String query = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Phone, customers.Postal_Code, first_level_divisions.Division, countries.Country FROM WJ06BqL.customers INNER JOIN WJ06BqL.first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID INNER JOIN WJ06BqL.countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID;";
            ResultSet results = statement.executeQuery(query);

            while(results.next()) {
                Customers customer = new Customers(
                        results.getInt("Customer_ID"),
                        results.getString("Customer_Name"),
                        results.getString("Address"),
                        results.getString("Postal_Code"),
                        results.getString("Phone"),
                        results.getString("Division"),
                        results.getString("Country"));
                allCustomers.add(customer);
            }

            return allCustomers;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    /** Saves the Customer to the Database.
     @param zip Passes the Zip that needs to be saved.
     @param state Passes the State that needs to be saved.
     @param phone Passes the Phone that needs to be saved.
     @param name Passes the Name that needs to be saved.
     @param address  Passes the Address that needs to be saved.
      */
    public static boolean saveCustomer(String name, String address, String zip, String phone, String state) {
        try {
            Statement statement = DBQuery.getStatement();

            String updatedUser = Models.Users.getUsername();

            String queryZero = "SELECT Division_ID FROM WJ06BqL.first_level_divisions WHERE Division = '"+ state + "';";
            String queryTwo = "SELECT Customer_ID FROM  WJ06BqL.customers;";
            int StateID = 0;

            ResultSet resultsOne = statement.executeQuery(queryZero);

        if(resultsOne.first()) {
            StateID = resultsOne.getInt("Division_ID");
        }

            ResultSet results = statement.executeQuery(queryTwo);
            int max = 0;
            int i = 0;

            System.out.println("Did this get here?");

            while (results.next()) {

                int temp = results.getInt("Customer_ID");
                if(temp > max){
                    max = temp;
                }
            }

                String queryOne = "INSERT INTO WJ06BqL.customers VALUES(" + (max + 1) + ",'"
                        + name + "','" + address + "','" + zip + "','" + phone + "',NOW(), '" + updatedUser + "', NOW(), '" + updatedUser + "'," + StateID + ")";
                int updateOne = statement.executeUpdate(queryOne);


            }

        catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
            }
            return false;

        }

    /** Updates the Customer in the Database.
     @param zip Passes the Zip that needs to be updated.
     @param state Passes the State that needs to be updated.
     @param phone Passes the Phone that needs to be updated.
     @param name Passes the Name that needs to be updated.
     @param address  Passes the Address that needs to be updated.
     @param id Passes the ID that needs to be updated.
     */
    public static boolean updateCustomer(int id, String name, String address, String zip, String phone, String state) {
        try {
            Statement statement = DBQuery.getStatement();
            String queryZero = "SELECT Division_ID FROM WJ06BqL.first_level_divisions WHERE Division = '"+ state + "';";
            int StateID = 0;
            ResultSet resultsOne = statement.executeQuery(queryZero);

            if(resultsOne.first()) {
                StateID = resultsOne.getInt("Division_ID");
            }

            String updatedUser = Models.Users.getUsername();

            String queryOne = "UPDATE WJ06BqL.customers SET Customer_Name ='" + name +"', Address ='"
                    + address + "', Postal_Code ='" + zip + "', Phone ='" + phone + "', Last_Updated_By = '" + updatedUser + "', Last_Update = NOW(),Division_ID = " + StateID + " WHERE Customer_ID ='" + id + "';";

            int updateOne = statement.executeUpdate(queryOne);

        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    /** Get customer Customer from the ID.
     @param id Passes the Customer ID.
      */
    public static Customers getCustomer(int id) {
        try {
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM customer WHERE Customer_ID='" + id + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                Customers customer = new Customers();
                customer.setCustomerName(results.getString("customerName"));

                return customer;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }

    /** Deletes the Customer from the database.
     @param id Passes the ID of the customer that is being deleted.
      */
    public static boolean deleteCustomer(int id) {
        try {
            Statement statement = DBQuery.getStatement();

            System.out.println("Is this getting here 2");

            String queryTwo = "DELETE FROM WJ06BqL.appointments WHERE Customer_ID =" + id;
            int updateTwo = statement.executeUpdate(queryTwo);
            String queryOne = "DELETE FROM WJ06BqL.customers WHERE Customer_ID=" + id;
            int updateOne = statement.executeUpdate(queryOne);

            System.out.println("There was " + updateOne + updateTwo + " lines affected");

        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

}
