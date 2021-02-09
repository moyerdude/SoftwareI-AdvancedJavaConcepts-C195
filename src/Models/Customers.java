package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/** This Class handles the Customers for this application.
 @author Stephen Moyer
 */
public final class Customers {

    private final SimpleIntegerProperty customerID = new SimpleIntegerProperty();
    private final SimpleStringProperty customerName = new SimpleStringProperty();
    private final SimpleStringProperty customerAddress = new SimpleStringProperty();
    private final SimpleStringProperty customerZip = new SimpleStringProperty();
    private final SimpleStringProperty customerPhone = new SimpleStringProperty();
    private final SimpleStringProperty customerState = new SimpleStringProperty();
    private final SimpleStringProperty customerCountry = new SimpleStringProperty();

    public Customers(){}

    /** This method sets all the customer info.
     @param address This passes the Customer Address.
     @param country This passes the Customer country.
     @param ID This passes the Customer ID.
     @param name This passes the Customer name.
     @param phone This passes the Customer phone.
     @param state This passes the Customer state.
     @param zip This passes the Customer ZIP.
     */
    public Customers(int ID, String name, String address,String zip, String phone,String state,String country){

        setCustomerID(ID);
        setCustomerName(name);
        setCustomerAddress(address);
        setCustomerZip(zip);
        setCustomerPhone(phone);
        setCustomerState(state);
        setCustomerCountry(country);

    }


    /** Get Customer ID.  */
    public int getCustomerID(){
        return customerID.get();
    }

    /** Get Customer Name.  */
    public String getCustomerName(){
        return customerName.get();
    }

    /** Get Customer Address.  */
    public String getCustomerAddress(){
        return customerAddress.get();
    }

    /** Get Customer ZIP.  */
    public String getCustomerZip(){
        return customerZip.get();
    }

    /** Get Customer Phone.  */
    public String getCustomerPhone(){
        return customerPhone.get();
    }

    /** Get Customer State.  */
    public String getCustomerState(){
        return customerState.get();
    }

    /** Get Customer Country.  */
    public String getCustomerCountry(){
        return customerCountry.get();
    }


    /** This Sets the Customer ID.
     @param customerID Passes the Customer ID. */
    public void setCustomerID(int customerID){
        this.customerID.set(customerID);
    }

    /** This Sets the Customer Name.
     @param customerName Passes the Customer Name. */
    public void setCustomerName(String customerName){
        this.customerName.set(customerName);
    }

    /** This Sets the Customer Address.
     @param customerAddress Passes the Customer Address. */
    public void setCustomerAddress(String customerAddress){
        this.customerAddress.set(customerAddress);
    }

    /** This Sets the Customer ZIP.
     @param customerZip Passes the Customer ZIP. */
    public void setCustomerZip(String customerZip){
        this.customerZip.set(customerZip);
    }

    /** This Sets the Customer Phone.
     @param customerPhone Passes the Customer Phone. */
    public void setCustomerPhone(String customerPhone){
        this.customerPhone.set(customerPhone);
    }

    /** This Sets the Customer State.
     @param customerState Passes the Customer State. */
    public void setCustomerState(String customerState){
        this.customerState.set(customerState);
    }

    /** This Sets the Customer Country.
     @param customerCountry Passes the Customer Country. */
    public void setCustomerCountry(String customerCountry){
        this.customerCountry.set(customerCountry);
    }

}
