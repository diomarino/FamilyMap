package com.marinoo.familymap.res;

import android.os.Build;

import androidx.annotation.RequiresApi;
import com.marinoo.familymap.model.Event;

import java.util.Arrays;
import java.util.Objects;

/**
 * Give response to EventIDService.
 */
public class EventRes {

    private Event[] data;

    /**
     * Username that is associated to person.
     */
    private String associatedUsername;

    /**
     * The id of event.
     */
    private String eventID;

    /**
     * The id of person.
     */
    private String personID;

    /**
     * The latitude of event.
     */
    private double latitude;

    /**
     * The longitude of event.
     */
    private double longitude;

    /**
     * The country of event.
     */
    private String country;

    /**
     * The city of event.
     */
    private String city;

    /**
     * The event type.
     */
    private String eventType;

    /**
     * The year of event.
     */
    private int year;

    /**
     * To print error and success message.
     */
    private String message;

    /**
     * Check success or fail.
     */
    private boolean success;

    public EventRes() {
        this.data = new Event[200];
        this.associatedUsername = "";
        this.eventID = "";
        this.personID = "";
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.country = "";
        this.city = "";
        this.eventType = "";
        this.year = 0;
        this.message = "";
        this.success = false;
    }

    public EventRes(Event event) {
        this.associatedUsername = event.getAssociatedUsername();
        this.eventID = event.getEventID();
        this.personID = event.getPersonID();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();
        this.message = "";
        this.success = true;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventRes eventRes = (EventRes) o;
        return Double.compare(eventRes.latitude, latitude) == 0 &&
                Double.compare(eventRes.longitude, longitude) == 0 &&
                year == eventRes.year &&
                success == eventRes.success &&
                Arrays.equals(data, eventRes.data) &&
                Objects.equals(associatedUsername, eventRes.associatedUsername) &&
                Objects.equals(eventID, eventRes.eventID) &&
                Objects.equals(personID, eventRes.personID) &&
                Objects.equals(country, eventRes.country) &&
                Objects.equals(city, eventRes.city) &&
                Objects.equals(eventType, eventRes.eventType) &&
                Objects.equals(message, eventRes.message);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        int result = Objects.hash(associatedUsername, eventID, personID, latitude, longitude, country, city, eventType, year, message, success);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
