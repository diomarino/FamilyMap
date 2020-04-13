package com.marinoo.familymap.res;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class PersonSuccessRes {

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
     * Check success or fail.
     */
    private boolean success;

    public PersonSuccessRes(PersonRes pResult) {
        this.associatedUsername = pResult.getAssociatedUsername();
        this.personID = pResult.getPersonID();
        this.firstName = pResult.getFirstName();
        this.lastName = pResult.getLastName();
        this.gender = pResult.getGender();
        this.fatherID = pResult.getFatherID();
        this.motherID = pResult.getMotherID();
        this.spouseID = pResult.getSpouseID();
        this.success = pResult.isSuccess();
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
        PersonSuccessRes that = (PersonSuccessRes) o;
        return success == that.success &&
                Objects.equals(associatedUsername, that.associatedUsername) &&
                Objects.equals(personID, that.personID) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(fatherID, that.fatherID) &&
                Objects.equals(motherID, that.motherID) &&
                Objects.equals(spouseID, that.spouseID);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(associatedUsername, personID, firstName, lastName, gender, fatherID, motherID, spouseID, success);
    }
}
