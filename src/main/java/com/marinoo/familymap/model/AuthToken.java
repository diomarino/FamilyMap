package com.marinoo.familymap.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;
import java.util.UUID;

/**
 * AuthToken is generated when login succeeds.
 * Stores the auth token.
 */
public class AuthToken {

    /**
     * The auth token.
     * It's unique.
     */
    private String token;

    /**
     * userName is associated to authToken.
     */
    private String userName;

    public AuthToken(String userName) {
        this.token = UUID.randomUUID().toString();
        this.userName = userName;
    }

    public AuthToken() {
        this.token = "";
        this.userName = "";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String authToken) {
        this.token = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        AuthToken authToken1 = (AuthToken) o;
        return Objects.equals(token, authToken1.token);
    }

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "AuthToken{" +
                "token='" + token + '\'' +
                '}';
    }
}
