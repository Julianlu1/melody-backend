package server.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import server.GeneralException;
import server.entity.Instrument;
import server.repository.InstrumentRepository;

import java.util.List;

@RestController
public class InstrumentController {

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private Gson gson;

    @GetMapping("/instrument")
    public ResponseEntity index(){
        try{
            List<Instrument> instruments = instrumentRepository.findAll();
            return ResponseEntity.ok(instruments);
        }catch(Exception e){
            GeneralException ex = new GeneralException("Oeps, er gaat iets fout");
            String jsonString = gson.toJson(ex);
            return new ResponseEntity<>(jsonString, HttpStatus.BAD_REQUEST);
        }
    }
}
