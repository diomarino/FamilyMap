package com.marinoo.familymap.req;

import com.marinoo.familymap.model.AuthToken;

/**
 * Make request to register.
 */
public class RegisterReq {
    /**
     * The username of the account.
     * It's unique.
     */
    private String userName;

    /**
     * The password of the account.
     */
    private String password;

    /**
     * Email used to create the account.
     */
    private String email;

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The user's gender.
     * Can only be "f" for female or "m" for male.
     */
    private String gender;

    /**
     * This User object will be initialized by calling createUser.
     */
    private AuthToken authToken;

    private String serverHost;

    private String serverPort;

    public RegisterReq() {
        this.userName = "";
        this.password = "";
        this.email = "";
        this.firstName = "";
        this.lastName = "";
        this.gender = "";
    }

    public RegisterReq(String userName, String password, String email, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
}
