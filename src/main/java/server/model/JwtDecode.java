package server.model;


// Model om Jwt om te zetten in object
public class JwtDecode {
    private String sub;
    private int id;

    public JwtDecode(String sub, int id) {
        this.sub = sub;
        this.id = id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
