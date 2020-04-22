package server;

import org.springframework.http.HttpStatus;

public class GeneralException {

    private String error;


    public GeneralException(String error){
        this.error = error;
    }
}
