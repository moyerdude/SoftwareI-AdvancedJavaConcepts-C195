package Models;

import javafx.beans.property.SimpleIntegerProperty;

/** This is the Class for First Level Divisions.
 @author Stephen Moyer
 */
public class FirstLevel {

    private final SimpleIntegerProperty divisionID = new SimpleIntegerProperty();
    private final SimpleIntegerProperty division = new SimpleIntegerProperty();
    private final SimpleIntegerProperty countryID = new SimpleIntegerProperty();

    public FirstLevel(){}

    /** This sets the First Level Division info.
     @param countryID Sets Country ID.
     @param division Sets Country Division.
     @param divisionID Sets Division ID.
      */
    public FirstLevel(int divisionID, String division, int countryID){

        setDivisionID(divisionID);
        setDivision(division);
        setCountryID(countryID);

    }

    /** Sets Division ID.
     @param divisionID Passes the Division ID */
    public void setDivisionID(int divisionID){
        this.divisionID.set(divisionID);
    }

    /** Sets the Division.
     @param division Passes the division. */
    public void setDivision(String division) {
        this.division.set(Integer.parseInt(division));
    }

    /** Sets the Country ID.
     @param countryID Passes the Country ID.
      */
    public void setCountryID(int countryID) {
        this.countryID.set(countryID);
    }

    /** Gets the Division ID.  */
    public int getDivisionID(){
        return divisionID.get();
       }

    /** Gets the Division.  */
    public int getDivision(){
        return division.get();
    }

    /** Gets the Country ID.  */
    public int getCountryID(){
        return countryID.get();
    }


}
