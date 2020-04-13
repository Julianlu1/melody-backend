package server.model;

import java.io.Serializable;

// Om een reponse te maken met een jwt en deze terugsturen naar de client
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;

    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
