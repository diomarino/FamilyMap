package com.marinoo.familymap.res;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

/**
 * Give response to FillService.
 */
public class FillRes {
    /**
     * To store userName.
     */
    private String userName;

    /**
     * Specify the number of generations.
     */
    private int numberOfGenerations;

    private int numberOfPersons;

    private int numberOfEvents;

    /**
     * To print error and success message.
     */
    private String message;

    /**
     * Check success or fail.
     */
    private boolean success;

    public FillRes() {
        this.userName = "";
        this.numberOfGenerations = -1;
        this.numberOfPersons = 0;
        this. numberOfEvents = 0;
        this.message = "";
        this.success = true;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNumberOfGenerations() {
        return numberOfGenerations;
    }

    public void setNumberOfGenerations(int numberOfGenerations) {
        this.numberOfGenerations = numberOfGenerations;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public int getNumberOfEvents() {
        return numberOfEvents;
    }

    public void setNumberOfEvents(int numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
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
        FillRes fillRes = (FillRes) o;
        return numberOfGenerations == fillRes.numberOfGenerations &&
                numberOfPersons == fillRes.numberOfPersons &&
                numberOfEvents == fillRes.numberOfEvents &&
                success == fillRes.success &&
                Objects.equals(userName, fillRes.userName) &&
                Objects.equals(message, fillRes.message);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(userName, numberOfGenerations, numberOfPersons, numberOfEvents, message, success);
    }
}
