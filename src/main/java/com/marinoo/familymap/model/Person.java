package com.marinoo.familymap.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

/**
 * Represents a person to store family history information.
 * A user will have at least one Person object which represents the user him or herself.
 */
public class Person {

    /**
     * Person's id.
     * It's unique.
     */
    private String personID;

    /**
     * Username that it's associated to.
     */
    private String associatedUsername;

    /**
     * Person's first name.
     */
    private String firstName;

    /**
     * Person's last name.
     */
    private String lastName;

    /**
     * Person's gender.
     * Can only be "f" for female or "m" for male.
     */
    private String gender;

    /**
     * Person's father id.
     * It's an optional field.
     */
    private String fatherID;

    /**
     * Person's mother id.
     * It's an optional field.
     */
    private String motherID;

    /**
     * Person's spouse id.
     * It's an optional field.
     */
    private String spouseID;

    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public Person() {
        this.personID = "";
        this.associatedUsername = "";
        this.firstName = "";
        this.lastName = "";
        this.gender = "";
        this.fatherID = "";
        this.motherID = "";
        this.spouseID = "";
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o - the reference object with which to compare.
     * @return true if the object is the same as the o argument; false otherwise.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personID, person.personID) &&
                Objects.equals(associatedUsername, person.associatedUsername) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(gender, person.gender) &&
                Objects.equals(fatherID, person.fatherID) &&
                Objects.equals(motherID, person.motherID) &&
                Objects.equals(spouseID, person.spouseID);
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
    }

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Person{" +
                "personID='" + personID + '\'' +
                ", associatedUsername='" + associatedUsername + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", fatherID='" + fatherID + '\'' +
                ", motherID='" + motherID + '\'' +
                ", spouseID='" + spouseID + '\'' +
                '}';
    }
}
