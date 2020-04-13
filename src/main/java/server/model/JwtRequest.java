package server.model;

import java.io.Serializable;

// Voor het opslaan van de username en password van de client
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;

    public JwtRequest() {

    }

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
