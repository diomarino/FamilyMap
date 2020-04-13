package com.marinoo.familymap.res;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.marinoo.familymap.model.Person;


import java.util.Arrays;
import java.util.Objects;

/**
 * Give response to PersonIDService.
 */
public class PersonRes {

    private Person[] data;

    /**
     * Username that is associated to person.
     */
    private String associatedUsername;

    /**
     * The id of person.
     */
    private String personID;

    /**
     * The first name.
     */
    private String firstName;

    /**
     * The last name.
     */
    private String lastName;

    /**
     * The gender.
     * "F" or "M".
     */
    private String gender;

    /**
     * If person has father.
     * The father's id.
     */
    private String fatherID;

    /**
     * If person has mother.
     * The mother's id.
     */
    private String motherID;

    /**
     * If person has spouse.
     * The spouse's id.
     */
    private String spouseID;

    /**
     * To print error and success message.
     */
    private String message;

    /**
     * Check success or fail.
     */
    private boolean success;

    public PersonRes() {
        this.data = new Person[1000];
        this.associatedUsername = "";
        this.personID = "";
        this.firstName = "";
        this.lastName = "";
        this.gender = "";
        this.fatherID = "";
        this.motherID = "";
        this.spouseID = "";
        this.message = "";
        this.success = false;
    }

    public PersonRes(Person person) {
        this.data = new Person[200];
        this.associatedUsername = person.getAssociatedUsername();
        this.personID = person.getPersonID();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.fatherID = person.getFatherID();
        this.motherID = person.getMotherID();
        this.spouseID = person.getMotherID();
        this.message = "";
        this.success = true;
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
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
        PersonRes personRes = (PersonRes) o;
        return success == personRes.success &&
                Arrays.equals(data, personRes.data) &&
                Objects.equals(associatedUsername, personRes.associatedUsername) &&
                Objects.equals(personID, personRes.personID) &&
                Objects.equals(firstName, personRes.firstName) &&
                Objects.equals(lastName, personRes.lastName) &&
                Objects.equals(gender, personRes.gender) &&
                Objects.equals(fatherID, personRes.fatherID) &&
                Objects.equals(motherID, personRes.motherID) &&
                Objects.equals(spouseID, personRes.spouseID) &&
                Objects.equals(message, personRes.message);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        int result = Objects.hash(associatedUsername, personID, firstName, lastName, gender, fatherID, motherID, spouseID, message, success);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
