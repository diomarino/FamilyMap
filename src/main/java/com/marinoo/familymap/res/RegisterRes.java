package com.marinoo.familymap.res;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.marinoo.familymap.model.AuthToken;

import java.util.Objects;

/**
 * Give response to RegisterService.
 */
public class RegisterRes {
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
     * To print error and success message.
     */
    private String message;

    public RegisterRes() {
        this.authToken = "";
        this.userName = "";
        this.personID = "";
        this.success = false;
        this.message = "";
    }

    public RegisterRes(AuthToken authToken) {
        this.authToken = authToken.getToken();
        this.userName = authToken.getUserName();
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
        RegisterRes that = (RegisterRes) o;
        return success == that.success &&
                Objects.equals(authToken, that.authToken) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(personID, that.personID) &&
                Objects.equals(message, that.message);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(authToken, userName, personID, success, message);
    }
}
