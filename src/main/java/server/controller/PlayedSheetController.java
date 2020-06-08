package server.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.GeneralException;
import server.entity.User;
import server.logic.JwtService;
import server.logic.PlayedSheetLogic;

@RestController
public class PlayedSheetController {

    private Gson gson = new Gson();

    @Autowired
    private JwtService jwtService;

    @Autowired
    PlayedSheetLogic playedSheetLogic;

    @PostMapping("playedsheet/mark/{sheetId}")
    public ResponseEntity markAsPlayed(@AuthenticationPrincipal User user, @PathVariable Integer sheetId){
        try{
            boolean isPlayed = playedSheetLogic.markAsPlayed(sheetId);
            if(isPlayed){
                return ResponseEntity.ok("Ongemarkeerd!");
            }else{
                return ResponseEntity.ok("Gemarkeerd als gespeeld!");
            }
        }catch(Exception e){
            GeneralException ex = new GeneralException("Oeps, er gaat iets fout");
            String jsonString = gson.toJson(ex);
            return new ResponseEntity<>(jsonString,HttpStatus.BAD_REQUEST);
        }
    }
}
