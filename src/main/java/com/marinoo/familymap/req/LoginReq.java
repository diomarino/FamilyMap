package com.marinoo.familymap.req;

/**
 * Make request to login.
 */
public class LoginReq {

    /**
     * The users username.
     */
    private String userName;

    /**
     * The users password.
     */
    private String password;

    private String serverHost;

    private String serverPort;

    public LoginReq() {
        this.userName = "";
        this.password = "";
    }

    public LoginReq(String userName, String password) {
        this.userName = userName;
        this.password = password;
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
