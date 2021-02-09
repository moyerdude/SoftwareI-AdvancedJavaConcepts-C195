package Utils;

import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/** This class handles all the database side of the appointment.
 @author Stephen Moyer
 */
public class AppointmentDB {

    /** This retrieves the Weekly appointments.  */
    public static ObservableList<Appointment> getWeeklyAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusWeeks(1);

        try {
            Statement statement = DBQuery.getStatement();
            String query = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Start, appointments.End, contacts.Contact_Name,appointments.Customer_ID, appointments.Type FROM WJ06BqL.appointments INNER JOIN WJ06BqL.contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE Start >= '" + begin + "%' AND End <= '" + end + "%';";

            ResultSet results = statement.executeQuery(query);
            while(results.next()) {

               String tempStart = results.getString("Start");
                String tempEnd = results.getString("End");

                tempStart = displayLocalTime(tempStart);
                tempEnd = displayLocalTime(tempEnd);

                appointment = new Appointment(
                        results.getInt("Appointment_ID"),
                        results.getInt("Customer_ID"),
                        tempStart,
                        tempEnd,
                        results.getString("Title"),
                        results.getString("Description"),
                        results.getString("Location"),
                        results.getString("Contact_Name"),
                        results.getString("Type"));
                appointments.add(appointment);
            }

            return appointments;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    /** This retrieves the Monthly appointments.  */
    public static ObservableList<Appointment> getMonthlyAppointments () {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1);
        try {
            Statement statement = DBQuery.getStatement();
            String query = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Start, appointments.End, contacts.Contact_Name,appointments.Customer_ID, appointments.Type FROM WJ06BqL.appointments INNER JOIN WJ06BqL.contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE Start >= '" + begin + "%' AND End <= '" + end + "%';";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) {

                String tempStart = results.getString("Start");
                String tempEnd = results.getString("End");

                tempStart = displayLocalTime(tempStart);
                tempEnd = displayLocalTime(tempEnd);

                appointment = new Appointment(
                        results.getInt("Appointment_ID"),
                        results.getInt("Customer_ID"),
                        tempStart,
                        tempEnd,
                        results.getString("Title"),
                        results.getString("Description"),
                        results.getString("Location"),
                        results.getString("Contact_Name"),
                        results.getString("Type"));
                appointments.add(appointment);
            }

            return appointments;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }


    }

    /**This gets all the appointments from the Database.  */
    public static ObservableList<Appointment> getAllAppointments () {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment appointment;
     //   LocalDate begin = LocalDate.now();
      //  LocalDate end = LocalDate.now().plusMonths(1);
        try {
            Statement statement = DBQuery.getStatement();
            String query = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Start, appointments.End, contacts.Contact_Name,appointments.Customer_ID, appointments.Type FROM WJ06BqL.appointments INNER JOIN WJ06BqL.contacts ON appointments.Contact_ID = contacts.Contact_ID;";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) {

                String tempStart = results.getString("Start");
                String tempEnd = results.getString("End");

                tempStart = displayLocalTime(tempStart);
                tempEnd = displayLocalTime(tempEnd);

                appointment = new Appointment(
                        results.getInt("Appointment_ID"),
                        results.getInt("Customer_ID"),
                        tempStart,
                        tempEnd,
                        results.getString("Title"),
                        results.getString("Description"),
                        results.getString("Location"),
                        results.getString("Contact_Name"),
                        results.getString("Type"));
                appointments.add(appointment);
            }

            return appointments;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }


    }




    /** This method saves appointments data to the databae.
     @param type Passes the appointment Type that needs to be saved.
     @param title Passes the appointment Title that needs to be saved.
     @param location Passes the appointment Location that needs to be saved.
     @param description Passes the appointment Description that needs to be saved.
     @param contact Passes the contact Type that needs to be saved.
     @param custId Passes the appointment Customer ID that needs to be saved.
     @param endDate Passes the appointment End Date that needs to be saved.
     @param endTime Passes the appointment End Time that needs to be saved.
     @param startDate Passes the appointment Start Date that needs to be saved.
     @param StartTime Passes the appointment Start Time that needs to be saved.
      */
    public static boolean saveAppointment(String custId, String startDate, String StartTime, String endDate,String endTime, String title, String description, String location, String contact, String type) {

        String tsStart = createTimeStamp(startDate, Utils.AppointmentDB.convertToEST(StartTime));
        String tsEnd = createTimeStamp(endDate, Utils.AppointmentDB.convertToEST(endTime));
        try {

            Statement statement = DBQuery.getStatement();

            String queryTwo = "SELECT Appointment_ID FROM  WJ06BqL.appointments;";
            ResultSet results = statement.executeQuery(queryTwo);
            int max = 0;
            int i = 0;

            while (results.next()) {

                int appointmentMax = results.getInt("Appointment_ID");
                if(appointmentMax > max){
                    max = appointmentMax;
                }
            }

            String USER = Models.Users.getUsername();

            String query = "INSERT INTO WJ06BqL.appointments SET Appointment_ID = '" + (max +1) +"',  Customer_ID='" + custId + "', Title='" + title + "', Description='" +
                    description + "', Contact_ID='" + getContactID(contact) + "', Location='" + location + "',Type = '" + type + "', Start='" + tsStart + "', End='" +
                    tsEnd + "', Create_Date=NOW(), Created_By='" + Models.Users.getUsername() + "', User_ID ='" + getUserID(USER) + "', Last_Update=NOW(), Last_Updated_By='" + Models.Users.getUsername() + "'";
            int update = statement.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    /** Update the selected Appointment Data in the Database.
     @param startDate Passes the appointment start Date.
     @param endTime Passes the appointment end time.
     @param custId Passes the appointment customer ID.
     @param contact Passes the appointment Contact.
     @param description Passes the appointment Description.
     @param location Passes the appointment Location.
     @param endDate Passes the appointment End Date.
     @param title Passes the appointment Title.
     @param type Passes the appointment Type.
     @param id Passes the appointment ID.
     @param StartTime Passes the appointment Start Time.

      */
    public static boolean updateAppointment(int id, String custId, String startDate, String StartTime, String endDate,String endTime, String title, String description, String location, String contact, String type) {

        String USER = Models.Users.getUsername();

        String tsStart = createTimeStamp(startDate, Utils.AppointmentDB.convertToEST(StartTime));
        String tsEnd = createTimeStamp(endDate, Utils.AppointmentDB.convertToEST(endTime));
        try {
            Statement statement = DBQuery.getStatement();
            String query = "UPDATE WJ06BqL.appointments SET Customer_ID='" + custId + "', Title='" + title + "', Description='" +
                    description + "', Contact_ID='" + getContactID(contact) + "', Location='" + location + "',Type = '" + type + "', Start='" + tsStart + "', End='" +
                    tsEnd + "', User_ID ='" + getUserID(USER) + "', Last_Update=NOW(), Last_Updated_By='" + Models.Users.getUsername() + "'WHERE Appointment_ID = "+id+";";
            int update = statement.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    /** Delete Selected appointment in the database.
     @param id Passes the ID of the appointments that needs to be deleted.
      */
    public static boolean deleteAppointment(int id) {
        try {
            Statement statement = DBQuery.getStatement();
            String query = "DELETE FROM WJ06BqL.appointments WHERE Appointment_ID = " + id;
            int update = statement.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    /** Creates the Time Stamp for the appointment.
     @param date Passes the Date of the Time Stamp.
     @param time Passes the Time of the Time Stamp.
     @return ts returns the Timestamp.
      */
    public static String createTimeStamp(String date, String time) {

        ZoneId z = ZoneId.systemDefault();

        String Temp = date + " " + time;

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        LocalDateTime ldt = LocalDateTime.parse(Temp, df);
        ZonedDateTime zdt = ldt.atZone(z);
        ZonedDateTime utcDate = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        ldt = utcDate.toLocalDateTime();
        Timestamp ts = Timestamp.valueOf(ldt);
        return ts.toString();
    }

    /** Checks for overlapping appointments for customers.
     @param startTime Passes the start to Check.
     @param endTime passes then end time to check.
     @param date Passes the date to Check.
     @param id Passes the ID to Check.
     @param Customer Passes the Customer to Check.
    */
    public static boolean overlappingAppointment(int id, String Customer, String date, String startTime, String endTime) {
        String start = createTimeStamp(date, Utils.AppointmentDB.convertToEST(startTime));
        String end = createTimeStamp(date, Utils.AppointmentDB.convertToEST(endTime));



        try {
            Statement statement = DBQuery.getStatement();

            String query = "SELECT * FROM WJ06BqL.appointments WHERE" +
                    " (" +
                    "(Start >= '" + start + "' AND Start < '"+end+"') OR " +
                    "(End > '" + start + "' AND End <= '"+end+"') OR " +
                    "(Start <= '" + start + "' AND End >= '"+end+"') " +
                    ") " +
                    " AND Customer_ID = '" + Customer + "' AND Appointment_ID <> '" + id + "'";
            System.out.println(query);
            ResultSet results = statement.executeQuery(query);
            return results.next();

        } catch (SQLException e) {
            System.out.println("SQLExcpection: " + e.getMessage());
            return true;
        }


    }

    /** Gets the UserID.
     @param user Psses the user as a string.
     @return id Returns the user ID.
      */
    private static int getUserID(String user){
        int id = 0;

        try {
            Statement statement = DBQuery.getStatement();
            String updatedUser = Models.Users.getUsername();
            String queryZero = "SELECT User_ID FROM WJ06BqL.users WHERE User_Name = '" + user + "';";
            ResultSet resultsOne = statement.executeQuery(queryZero);
            if(resultsOne.first()) {
                id = resultsOne.getInt("User_ID");
            }

        }
        catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }

        return id;
    }

    /** Gets the Contact list.
     @param contactList Passes the Contact list.
     @return contactList Returns the Contact list.
      */
    public static ObservableList<String> getContactList(ObservableList<String> contactList){

        try{

            Statement statement = DBQuery.getStatement();
            String queryTwo = "SELECT * FROM WJ06BqL.contacts;";
            ResultSet results = statement.executeQuery(queryTwo);

            while (results.next()) {

                String temp = results.getString("Contact_Name");
                contactList.add(temp);
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }


        return contactList;
    }

    /** Get the Contact ID.
     @param contact Passes the contact name.
     @return id Returns the ID of the contact.
      */
    private static int getContactID(String contact){
        int id = 0;

        try {
            Statement statement = DBQuery.getStatement();

            String updatedUser = Models.Users.getUsername();

            String queryZero = "SELECT Contact_ID FROM WJ06BqL.contacts WHERE Contact_Name = '" + contact + "';";

            ResultSet resultsOne = statement.executeQuery(queryZero);

            if(resultsOne.first()) {
                id = resultsOne.getInt("Contact_ID");
            }

        }
        catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }
        return id;
    }


    /** Gets the 8:00 - 20:00 EST.
     @param LT Passes the list of times.
     @return LT Returns the list of times.
     */
    public static ObservableList<String> getLocalTimes(ObservableList<String> LT){

        LocalTime temp1 = LocalTime.parse("08:00");

        while((LocalTime.parse("20:01")).isAfter(temp1)) {

            LT.add(temp1.toString());
            temp1 = temp1.plusMinutes(15);

        }

        return LT;
    }

    /** Displays the Local Time.
     @param DATETIME Passes the date and Time.
     @return ts returns the Time Stamp.
      */
    public static String displayLocalTime(String DATETIME){

        ZoneId x = ZoneId.systemDefault();

        DATETIME = DATETIME.substring(0,DATETIME.length()-5);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        LocalDateTime ldt = LocalDateTime.parse(DATETIME,df);

        ZonedDateTime zdt = ldt.atZone((ZoneId.of("UTC")));
        ZonedDateTime utcDate = zdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        ldt = utcDate.toLocalDateTime();

        Timestamp ts = Timestamp.valueOf(ldt);

        return ts.toString();

    }

    /** Checks if there is an Appointment within 15 mins of the users log in.
      @return appointment Returns the appointment that is in 15 mins.
      */
    public static Appointment appointmentIn15Min() {
        Appointment appointment;
        LocalDateTime now = LocalDateTime.now();
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime zdt = now.atZone(zid);
        LocalDateTime ldt = zdt.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        System.out.println(ldt);

        LocalDateTime ldt2 = ldt.plusMinutes(15);
        System.out.println(ldt2);
        String user = DBQuery.getCurrentUser().getUsername();
        System.out.print(user);
        int u = getUserID(user);
        System.out.print(u);
        try {
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM WJ06BqL.appointments WHERE Start BETWEEN '" + ldt + "' AND '" + ldt2 + "' AND " +
                    "User_ID ='" + u + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                appointment = new Appointment(
                        results.getInt("Appointment_ID"),
                        results.getInt("Customer_ID"),
                        results.getString("Start"),
                        results.getString("End"),
                        results.getString("Title"),
                        results.getString("Description"),
                        results.getString("Location"),
                        results.getString("Contact_ID"),
                        results.getString("Type"));
                return appointment;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }

    /** Check if there the selected dates are on the weekend.
     @param start Passes the start date.
     @param end Passes the end date.
     @param weekend passes a T or F value.
      */
    public static boolean noWeekEnds(String start, String end, boolean weekend){

        LocalDate ls = LocalDate.parse(start);
        LocalDate le = LocalDate.parse(end);

        if(ls.getDayOfWeek() == DayOfWeek.SATURDAY || ls.getDayOfWeek() == DayOfWeek.SUNDAY || le.getDayOfWeek() == DayOfWeek.SATURDAY || le.getDayOfWeek() == DayOfWeek.SUNDAY){
           return true;
        }
        else {
            return false;
        }
    }

    /** Converts time to EST timezone.
     @param time Passes the Time that needs to be converted.
     return temp1 Returns the new time.
      */
    public static String convertToEST(String time){

        LocalDate date = LocalDate.now();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        String T = (date.toString()) +" "+time;
        LocalDateTime ldt = LocalDateTime.parse(T, df);

        ZonedDateTime zdt = ldt.atZone((ZoneId.of("America/New_York")));
        ZonedDateTime utcDate = zdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        ldt = utcDate.toLocalDateTime();
        Timestamp ts = Timestamp.valueOf(ldt);
        String temp = ts.toString();
        temp = temp.substring(0,temp.length()-5);
        temp = temp.substring(11);
        LocalTime temp1 = LocalTime.parse(temp);


        return temp1.toString();
    }

    /** Converts the local timezone.
     @param time Passes the time that needs to be converted.
     @return temp1 returns the converted time.
      */
    public static String convertToLocal(String time){

        LocalDate date = LocalDate.now();
        System.out.println("Alert this is the temp date: "+date);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        String T = (date.toString()) +" "+time;
        System.out.println("Alert T = " + T);
        LocalDateTime ldt = LocalDateTime.parse(T, df);
        System.out.println(ldt);
        ZonedDateTime zdt = ldt.atZone((ZoneId.of(ZoneId.systemDefault().toString())));
        System.out.println(zdt);
        ZonedDateTime utcDate = zdt.withZoneSameInstant(ZoneId.of("America/New_York"));
        ldt = utcDate.toLocalDateTime();
        Timestamp ts = Timestamp.valueOf(ldt);
        String temp = ts.toString();
        temp = temp.substring(0,temp.length()-5);
        temp = temp.substring(11);
        LocalTime temp1 = LocalTime.parse(temp);
        System.out.println("Alert this is the temp time: "+temp);

        return temp1.toString();
    }



}
