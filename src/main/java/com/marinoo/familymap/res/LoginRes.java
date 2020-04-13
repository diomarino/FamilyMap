package com.marinoo.familymap.res;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.marinoo.familymap.model.AuthToken;


import java.util.Objects;

/**
 * Give response to LoginService.
 */
public class LoginRes {
    /**
     * To store authToken.
     */
    private String authToken;

    /**
     * To store userName;
     */
    private String userName;

    /**
     * To store personID.
     */
    private String personID;

    /**
     * To check if success or fail.
     */
    private boolean success;

    /**
     * The print error and success message.
     */
    private String message;

    public LoginRes() {
        this.authToken = "";
        this.userName = "";
        this.personID = "";
        this.success = false;
        this.message = "";
    }

    public LoginRes(AuthToken authToken) {
        this.authToken = authToken.getToken();
        this.userName = authToken.getUserName();
        this.personID = "";
        this.success = true;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonId() {
        return personID;
    }

    public void setPersonId(String personId) {
        this.personID = personId;
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
        LoginRes loginRes = (LoginRes) o;
        return success == loginRes.success &&
                Objects.equals(authToken, loginRes.authToken) &&
                Objects.equals(userName, loginRes.userName) &&
                Objects.equals(personID, loginRes.personID) &&
                Objects.equals(message, loginRes.message);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(authToken, userName, personID, success, message);
    }
}
