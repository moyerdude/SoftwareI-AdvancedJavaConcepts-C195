package Utils;

import Models.FirstLevel;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Gets the states list.
 @author Stephen Moyer
 */
public class State {

    private static ObservableList<FirstLevel> state = FXCollections.observableArrayList();

    /** Get the updates state list.
     @param country Passes the country
     @param stateText Passes the state text
      */
    public static ObservableList<String> getSateList(ObservableList<String> stateText, String country) {

        try {

            Statement statement = DBQuery.getStatement();
            String queryOne = "SELECT * FROM WJ06BqL.countries WHERE Country = '" + country + "';";

            ResultSet resultsOne = statement.executeQuery(queryOne);
            int countryId = 0;
            if (resultsOne.first()) {
                countryId = resultsOne.getInt("Country_ID");
            }

            String queryTwo = "SELECT * FROM WJ06BqL.first_level_divisions WHERE COUNTRY_ID =" + countryId + ";";
            ResultSet results = statement.executeQuery(queryTwo);

            while (results.next()) {

                String temp = results.getString("Division");
                stateText.add(temp);
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return (stateText);
    }

    /** Get the Country Text.
     @param countryText Passes a varable to store the country.
      */
    public static ObservableList<String> getCountryText(ObservableList<String> countryText){

        try{

            Statement statement = DBQuery.getStatement();
            String queryTwo = "SELECT * FROM WJ06BqL.countries;";
            ResultSet results = statement.executeQuery(queryTwo);

            while (results.next()) {

                String temp = results.getString("Country");
                countryText.add(temp);
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return countryText;
    }


    /** Sets the Country.
     @param country Passes the country.
      */
    public static String setCountry(String country){

        try{
            int id = 0;

            Statement statement = DBQuery.getStatement();
            String queryTwo = "SELECT * FROM WJ06BqL.first_level_divisions WHERE division = '" + country +"';";
            ResultSet results = statement.executeQuery(queryTwo);

            if(results.first()) {
                country = results.getString("COUNTRY_ID");
            }

            Statement s = DBQuery.getStatement();
            String queryOne = "SELECT * FROM WJ06BqL.countries WHERE Country_ID = " + country;
            ResultSet rs = s.executeQuery(queryOne);
            if(rs.first()){
                country = rs.getString("Country");
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return country;
    }

}






