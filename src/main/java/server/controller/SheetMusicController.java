package server.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.GeneralException;
import server.entity.Instrument;
import server.entity.SheetMusic;
import server.entity.User;
import server.logic.InstrumentLogic;
import server.logic.SheetMusicLogic;
import server.repository.InstrumentRepository;
import server.repository.SheetMusicRepository;
import javax.servlet.ServletContext;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

@RestController
public class SheetMusicController {
    @Autowired
    private Gson gson;

    @Autowired
    private SheetMusicLogic sheetMusicLogic;

    @Autowired
    private InstrumentLogic instrumentLogic;

    @Autowired
    ServletContext context;

    @Autowired
    ServletContext servletContext;

    @Autowired
    SheetMusicRepository sheetMusicRepository;

    @Autowired
    InstrumentRepository instrumentRepository;

    @GetMapping("/sheetmusic")
    public ResponseEntity findAll(){
        try{
            List<SheetMusic> sheetMusics = sheetMusicLogic.findAll();
            return ResponseEntity.ok(sheetMusics);
        }catch(Exception e){
            GeneralException ex = new GeneralException("Oeps, er gaat iets fout");
            String jsonString = gson.toJson(ex);
            return new ResponseEntity<>(jsonString,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sheetmusic/{id}")
    public ResponseEntity findById(@PathVariable String id){
        int sheetId = Integer.parseInt(id);

        try{
            SheetMusic sheetMusic2 = sheetMusicLogic.findById(sheetId);
            if(sheetMusic2 != null){
                return ResponseEntity.ok(sheetMusic2);
            }else{
                return new ResponseEntity("Sheet bestaat niet",HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            GeneralException ex = new GeneralException("Oeps, er gaat iets fout");
            String jsonString = gson.toJson(ex);
            return new ResponseEntity(jsonString,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/sheetmusic")
    public ResponseEntity create(@RequestParam("file") MultipartFile file, @RequestParam("title") String title, @RequestParam("componist") String componist, @RequestParam("key") String key, @RequestParam("instrument_id") int instrument_id) throws IOException {

        try{
            Instrument instrument = instrumentLogic.findById(instrument_id);
            SheetMusic sheetMusic = sheetMusicLogic.addSheetMusic(title,componist,key,file.getOriginalFilename(),file,instrument);
            return ResponseEntity.ok(sheetMusic);
        }catch(Exception e){
            GeneralException ex = new GeneralException("Oeps, er gaat iets fout");
            String jsonString = gson.toJson(ex);
            return new ResponseEntity<>(jsonString,HttpStatus.BAD_REQUEST);
        }
        //        Uploads to target/static
//        String filePath = ResourceUtils.getFile("classpath:static").toString();
//        fileService.uploadFile(file,filePath);
    }

    @DeleteMapping("/sheetmusic/{id}")
    public ResponseEntity deleteById(@PathVariable String id){
        int sheetId = Integer.parseInt(id);
        try{
            sheetMusicLogic.deleteById(sheetId);
            return ResponseEntity.ok("deleted");
        }catch(Exception e){
            GeneralException ex = new GeneralException("Oeps, er gaat iets fout");
            String jsonString = gson.toJson(ex);
            return new ResponseEntity<>(jsonString,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/sheetmusic/")
    public ResponseEntity deleteAll(){
        try{
            sheetMusicLogic.deleteAll();
            return ResponseEntity.ok("Deleted all");
        }catch(Exception e){
            GeneralException ex = new GeneralException("Oeps, er gaat iets fout");
            String jsonString = gson.toJson(ex);
            return new ResponseEntity<>(jsonString,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sheetmusic/filter")
    public ResponseEntity findSheetMusicByFilter(@RequestParam(required = false) String componist, @RequestParam(required=false) String key, @RequestParam(required = false) Integer instrument_id){
        try{
            List<SheetMusic> sheetMusics = sheetMusicLogic.findSheetMusicByFilter(componist,key,instrument_id);
            return ResponseEntity.ok(sheetMusics);
        }catch(Exception e){
            GeneralException ex = new GeneralException("Oeps, er gaat iets fout");
            String jsonString = gson.toJson(ex);
            return new ResponseEntity<>(jsonString,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/sheetmusic/played")
    public ResponseEntity markAsPlayed(@AuthenticationPrincipal User user, int sheetId){
        try{
            SheetMusic sheetMusic = sheetMusicLogic.markAsPlayed(user,sheetId);
            return ResponseEntity.ok(sheetMusic);
        }catch(Exception e){
            GeneralException ex = new GeneralException("Oeps, er gaat iets fout");
            String jsonString = gson.toJson(ex);
            return new ResponseEntity<>(jsonString,HttpStatus.BAD_REQUEST);
        }
    }

}