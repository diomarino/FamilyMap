package com.marinoo.familymap.res;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

/**
 * Give response to LoadService.
 */
public class LoadRes {
    /**
     * Indicates how many users successfully loaded.
     */
    private int numberOfUsers;

    /**
     * Indicates how many persons successfully loaded.
     */
    private int numberOfPersons;

    /**
     * Indicates how many events successfully loaded.
     */
    private int numberOfEvents;


    /**
     * To print error and success message.
     */
    private String message;

    /**
     * Check success or fail.
     */
    private boolean success;

    public LoadRes() {
        this.numberOfUsers = 0;
        this.numberOfPersons = 0;
        this.numberOfEvents = 0;
        this.success = false;
        this.message = "";
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoadRes loadRes = (LoadRes) o;
        return numberOfUsers == loadRes.numberOfUsers &&
                numberOfPersons == loadRes.numberOfPersons &&
                numberOfEvents == loadRes.numberOfEvents &&
                success == loadRes.success &&
                Objects.equals(message, loadRes.message);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(numberOfUsers, numberOfPersons, numberOfEvents, message, success);
    }
}
