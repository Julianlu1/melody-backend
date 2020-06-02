package server.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import server.GeneralException;
import server.entity.Instrument;
import server.logic.InstrumentLogic;
import server.repository.InstrumentRepository;

import java.util.List;

@RestController
public class InstrumentController {

    @Autowired
    private InstrumentRepository instrumentRepository;

    private InstrumentLogic instrumentLogic;


    @Autowired
    private Gson gson;

    @Autowired
    public InstrumentController(InstrumentLogic instrumentLogic) {
        this.instrumentLogic = instrumentLogic;
    }

    @GetMapping("/instrument")
    public ResponseEntity getAllInstruments(){
        try{
            List<Instrument> instruments = instrumentLogic.findAll();
            return ResponseEntity.ok(instruments);
        }catch(Exception e){
            GeneralException ex = new GeneralException("Oeps, er gaat iets fout");
            String jsonString = gson.toJson(ex);
            return new ResponseEntity<>(jsonString, HttpStatus.BAD_REQUEST);
        }
    }
}
