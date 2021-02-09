package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/** This is the Appointment class that takes care of appointments.
   @author Stephen Moyer
  */
public class Appointment {

    private final SimpleIntegerProperty aptId = new SimpleIntegerProperty();
    private final SimpleIntegerProperty aptCustomerId = new SimpleIntegerProperty();
    private final SimpleStringProperty aptStart = new SimpleStringProperty();
    private final SimpleStringProperty aptEnd = new SimpleStringProperty();
    private final SimpleStringProperty aptTitle = new SimpleStringProperty();
    private final SimpleStringProperty aptDescription = new SimpleStringProperty();
    private final SimpleStringProperty aptLocation = new SimpleStringProperty();
    private final SimpleStringProperty aptContact = new SimpleStringProperty();
    private final SimpleStringProperty aptType = new SimpleStringProperty();


    public Appointment() {}

    /** This sets all the items of the appointment.
     Sets every element of an appointment.
     @param id Holds the Appointment ID
     @param custId Holds the Appointment customer ID
     @param contact Holds the Appointment Contact
     @param description Holds the Appointment description
     @param end Holds the Appointment end
     @param location Holds the Appointment location
     @param start Holds the Appointment start
     @param title Holds the Appointment title
     @param type Holds the Appointment type
      */
    public Appointment(int id, int custId, String start, String end, String title, String description, String location, String contact, String type) {
        setAptId(id);
        setAptCustomerId(custId);
        setAptStart(start);
        setAptEnd(end);
        setAptTitle(title);
        setAptDescription(description);
        setAptLocation(location);
        setAptContact(contact);
        setAptType(type);
    }

    /** Gets the appointment End property.  */
    public SimpleStringProperty getAptEndProperty() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime ldt = LocalDateTime.parse(this.aptEnd.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid);
        SimpleStringProperty date = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
        return date;
    }

    /** Gets the appointment Title property.  */
    public SimpleStringProperty getAptTitleProperty() {
        return this.aptTitle;
    }

    /** Gets the appointment Description property.  */
    public SimpleStringProperty getAptDescriptionProperty() {
        return this.aptDescription;
    }

    /** Gets the appointment Location property.  */
    public SimpleStringProperty getAptLocationProperty() {
        return this.aptLocation;
    }

    /** Gets the appointment Contact property.  */
    public SimpleStringProperty getAptContactProperty() {
        return this.aptContact;
    }

    /** Gets the appointment Start.  */
    public String getAptStart() {
        return aptStart.get();
    }

    /** Gets the appointment End.  */
    public String getAptEnd() {
        return aptEnd.get();
    }

    /** Gets the appointment ID.  */
    public int getAptId() {
        return aptId.get();
    }

    /** Gets the appointment Customer ID.  */
    public int getAptCustomerId() {
        return aptCustomerId.get();
    }

    /** Gets the appointment Type.  */
    public String getAptType(){
        return aptType.get();
    }

    /** Gets the appointment Title.  */
    public String getAptTitle() {
        return aptTitle.get();
    }

    /** Gets the appointment Description.  */
    public String getAptDescription() {
        return aptDescription.get();
    }

    /** Gets the appointment Location.  */
    public String getAptLocation() {
        return aptLocation.get();
    }

    /** Gets the appointment Contact.  */
    public String getAptContact() {
        return aptContact.get();
    }

    /** Sets the Appointment ID.
      @param aptId This is the Appointment ID*/
    public void setAptId(int aptId) {
        this.aptId.set(aptId);
    }

    /** Sets the Appointment Customer ID.
     @param aptCustomerId This is the Appointment Customer ID*/
    public void setAptCustomerId(int aptCustomerId) {
        this.aptCustomerId.set(aptCustomerId);
    }

    /** Sets the Appointment End.
     @param aptEnd This is the Appointment End*/
    public void setAptEnd(String aptEnd) {
        this.aptEnd.set(aptEnd);
    }

    /** Sets the Appointment Start.
     @param aptTimeStart This is the Appointment Start*/
    public void setAptStart(String aptTimeStart) {
        this.aptStart.set(aptTimeStart);
    }

    /** Sets the Appointment Title.
     @param aptTitle This is the Appointment Title*/
    public void setAptTitle(String aptTitle) {
        this.aptTitle.set(aptTitle);
    }

    /** Sets the Appointment Description.
     @param aptDescription This is the Appointment Description*/
    public void setAptDescription(String aptDescription) {
        this.aptDescription.set(aptDescription);
    }

    /** Sets the Appointment Location.
     @param aptLocation This is the Appointment Location*/
    public void setAptLocation(String aptLocation) {
        this.aptLocation.set(aptLocation);
    }

    /** Sets the Appointment Contact.
     @param aptContact This is the Appointment Contact*/
    public void setAptContact(String aptContact) {
        this.aptContact.set(aptContact);
    }

    /** Sets the Appointment Type.
     @param aptType This is the Appointment Type*/
    public void setAptType (String aptType){
        this.aptType.set(aptType);
    }

    /** Sets the Appointment Start date only.  */
    public LocalDate getStartDateOnly() {
        Timestamp ts = Timestamp.valueOf(this.aptStart.get());
        ZonedDateTime zdt;
        ZoneId zid;
        LocalDate ld;
            zid = ZoneId.of(ZoneId.systemDefault().toString());

        zdt = ts.toLocalDateTime().atZone(zid);
        ld = zdt.toLocalDate();
        return ld;
    }

    /** This method gets the Appointment Start Property.  */
    public StringProperty getAptStartProperty() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime ldt = LocalDateTime.parse(this.aptStart.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid);
        StringProperty date = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
        return date;
    }

    /** Gets the Appointments End Date Only.  */
    public LocalDate getEndDateOnly() {
        Timestamp ts = Timestamp.valueOf(this.aptEnd.get());
        ZonedDateTime zdt;
        ZoneId zid;
        LocalDate ld;
        zid = ZoneId.of(ZoneId.systemDefault().toString());
        zdt = ts.toLocalDateTime().atZone(zid);
        ld = zdt.toLocalDate();
        return ld;
    }

    /** This method gets the Appointment Start Time only.
     Returns the Appointment End time.
     @return ldt Returns the Time.
      */
    public String getStartTimeOnly() {
        Timestamp ts = Timestamp.valueOf(this.aptStart.get());
        ZoneId x = ZoneId.systemDefault();

        String temp = ts.toString();
        temp = temp.substring(0,temp.length()-5);
        temp = temp.substring(11);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("kk:mm");
        LocalTime ldt = LocalTime.parse(temp, df);
        return ldt.toString();

    }

    /** This method gets the Appointment End Time only.
     Returns the Appointment End time.
     @return ldt Returns the Time.
     */
    public String getEndTimeOnly() {
        Timestamp ts = Timestamp.valueOf(this.aptEnd.get());
        ZoneId x = ZoneId.systemDefault();
        ZoneId zid;
        LocalTime lt;

        String temp = ts.toString();

        temp = temp.substring(0,temp.length()-5);
        temp = temp.substring(11);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("kk:mm");

        LocalTime ldt = LocalTime.parse(temp, df);

        return ldt.toString();
    }


}
