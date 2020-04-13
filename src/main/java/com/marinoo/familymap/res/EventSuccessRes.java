package com.marinoo.familymap.res;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class EventSuccessRes {

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
     * Check success or fail.
     */
    private boolean success;

    public EventSuccessRes(EventRes eventRes) {
        this.associatedUsername = eventRes.getAssociatedUsername();
        this.eventID = eventRes.getEventID();
        this.personID = eventRes.getPersonID();
        this.latitude = eventRes.getLatitude();
        this.longitude = eventRes.getLongitude();
        this.country = eventRes.getCountry();
        this.city = eventRes.getCity();
        this.eventType = eventRes.getEventType();
        this.year = eventRes.getYear();
        this.success = eventRes.isSuccess();
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
        EventSuccessRes that = (EventSuccessRes) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                year == that.year &&
                success == that.success &&
                Objects.equals(associatedUsername, that.associatedUsername) &&
                Objects.equals(eventID, that.eventID) &&
                Objects.equals(personID, that.personID) &&
                Objects.equals(country, that.country) &&
                Objects.equals(city, that.city) &&
                Objects.equals(eventType, that.eventType);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(associatedUsername, eventID, personID, latitude, longitude, country, city, eventType, year, success);
    }
}
