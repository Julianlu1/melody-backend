package server.model;

public class UserTest {
    private String username;

    private String password;

    private String token;

    public UserTest(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
