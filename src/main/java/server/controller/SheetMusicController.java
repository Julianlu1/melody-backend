package server.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.GeneralException;
import server.entity.SheetMusic;
import server.logic.SheetMusicLogic;
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
    ServletContext context;

    @Autowired
    ServletContext servletContext;

    @Autowired
    SheetMusicRepository sheetMusicRepository;

    @GetMapping("/sheetmusic")
    public ResponseEntity index(){
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
    public ResponseEntity getById(@PathVariable String id){
        int sheetId = Integer.parseInt(id);

        try{
            SheetMusic sheetMusic2 = sheetMusicLogic.findById(sheetId);
            return ResponseEntity.ok(sheetMusic2);
        }catch(Exception e){
            GeneralException ex = new GeneralException("Oeps, er gaat iets fout");
            String jsonString = gson.toJson(ex);
            return new ResponseEntity(jsonString,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/sheetmusic")
    public ResponseEntity create(@RequestParam("file") MultipartFile file, @RequestParam("title") String title, @RequestParam("componist") String componist, @RequestParam("key") String key, @RequestParam("instrument") String instrument) throws IOException {

        try{
            SheetMusic sheetMusic = sheetMusicLogic.addSheetMusic(title,componist,key,instrument,file.getOriginalFilename(),file);
            return ResponseEntity.ok(sheetMusic);
        }catch(Exception e){
            GeneralException ex = new GeneralException("Oeps, er gaat iets fout");
            String jsonString = gson.toJson(ex);
            return new ResponseEntity(jsonString,HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity(jsonString,HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity(jsonString,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sheetmusic/filter")
    public ResponseEntity findSheetMusicByFilter(@RequestParam(required = false) String componist,@RequestParam(required = false) String instrument){

        String key= null;

        try{
            List<SheetMusic> sheetMusics = sheetMusicLogic.findSheetMusicByFilter(componist,key,instrument);
            return ResponseEntity.ok(sheetMusics);
        }catch(Exception e){
            GeneralException ex = new GeneralException("Oeps, er gaat iets fout");
            String jsonString = gson.toJson(ex);
            return new ResponseEntity(jsonString,HttpStatus.BAD_REQUEST);
        }
    }
}