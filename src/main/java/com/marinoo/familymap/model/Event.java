package com.marinoo.familymap.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;
import java.util.UUID;

/**
 * Stores important events and its details.
 */
public class Event {

    /**
     * Event id.
     * It's unique.
     */
    private String eventID;

    /**
     * Username that it's associated to.
     */
    private String associatedUsername;

    /**
     * Person's id to know whose event.
     */
    private String personID;

    /**
     * The latitude where the even take place.
     * It can go up to 16 decimal digit.
     */
    private double latitude;

    /**
     * The longitude where the event take place.
     * It can go up to 16 decimal digit.
     */
    private double longitude;

    /**
     * Country where the event take place.
     */
    private String country;

    /**
     * The city where the event take place.
     */
    private String city;

    /**
     * Describes the event.
     */
    private String eventType;

    /**
     * Year when the event happened.
     */
    private int year;

    public Event(String associatedUsername, String personID, double latitude, double longitude, String country, String city, String eventType, int year) {
        this.eventID = UUID.randomUUID().toString();
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public Event() {
        this.eventID = "";
        this.associatedUsername = "";
        this.personID = "";
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.country = "";
        this.city = "";
        this.eventType = "";
        this.year = 0;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year);
    }

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Event{" +
                "eventID='" + eventID + '\'' +
                ", associatedUsername='" + associatedUsername + '\'' +
                ", personID='" + personID + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", eventType='" + eventType + '\'' +
                ", year=" + year +
                '}';
    }
}