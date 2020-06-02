package server.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.GeneralException;
import server.entity.PlayedSheet;
import server.logic.JwtService;
import server.repository.PlayedSheetRepository;

import java.util.List;
import java.util.Map;

@RestController
public class PlayedSheetController {

    private Gson gson = new Gson();

    @Autowired
    private JwtService jwtService;

    @Autowired
    PlayedSheetRepository playedSheetRepository;


    @GetMapping("/played")
    public ResponseEntity index(@RequestHeader(name="Authorization") String token){
        jwtService.decodeJwt(token);
        int userId = jwtService.getIdFromToken();

        try{
            List<PlayedSheet> playedSheets = playedSheetRepository.findByUserId(userId);
            return ResponseEntity.ok(playedSheets);

        }catch(Exception e){
            GeneralException generalException = new GeneralException("Oeps, er gaat iets fout");
            String json = gson.toJson(generalException);
            return new ResponseEntity(json, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/played")
    public ResponseEntity create(@RequestHeader(name = "Authorization") String token, @RequestBody Map<String,String> body){
        jwtService.decodeJwt(token);
        int userId = jwtService.getIdFromToken();

        int sheetId = Integer.parseInt(body.get("sheetId"));
        try{
            PlayedSheet playedSheet = new PlayedSheet(sheetId,userId);
            playedSheetRepository.save(playedSheet);
            return ResponseEntity.ok(playedSheet);
        }catch(Exception e){
            GeneralException generalException = new GeneralException("Oeps, er gaat iets fout");
            String json = gson.toJson(generalException);
            return new ResponseEntity(json, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/played")
    public ResponseEntity delete(@RequestHeader(name = "Authorization") String token, @RequestBody Map<String,String> body){
        jwtService.decodeJwt(token);
        int userId = jwtService.getIdFromToken();
        int sheetId = Integer.parseInt(body.get("sheetId"));

        try{
            playedSheetRepository.deleteByUserIdAndSheetMusicId(userId,sheetId);
            return ResponseEntity.ok("Deleted");
        }catch(Exception e){
            GeneralException generalException = new GeneralException("Oeps, er gaat iets fout");
            String json = gson.toJson(generalException);
            return new ResponseEntity(json, HttpStatus.BAD_REQUEST);
        }
    }
}
