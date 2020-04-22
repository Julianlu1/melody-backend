package server.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.GeneralException;
import server.entity.SheetMusic;
import server.logic.FileService;
import server.logic.SheetMusicLogic;
import server.repository.SheetMusicRepository;
import javax.servlet.ServletContext;
import java.io.*;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@RestController
public class SheetMusicController {
    @Autowired
    private Gson gson;
    @Autowired
    private ResourceLoader resourceLoader;

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
            Optional<SheetMusic> sheetMusic2 = sheetMusicLogic.findById(sheetId);
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
    public String deleteById(@PathVariable String id){
        int sheetId = Integer.parseInt(id);
        sheetMusicRepository.deleteById(sheetId);
        return "";
    }

    @DeleteMapping("/sheetmusic/all")
    public String deleteById(){
        sheetMusicRepository.deleteAll();
        return "Deleted all";
    }

    @GetMapping("/sheetmusic/filter")
    public List<SheetMusic> getSheetMusicByFilter(@RequestBody Map<String,String> body){
        String componist = body.get("componist");
        String key = body.get("key");
        String instrument = body.get("instrument");

        SheetMusic sheetMusic = new SheetMusic(componist,key,instrument);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("componist", new ExampleMatcher.GenericPropertyMatcher().exact())
                .withMatcher("key",new ExampleMatcher.GenericPropertyMatcher().exact())
                .withMatcher("instrument",new ExampleMatcher.GenericPropertyMatcher().exact());

        Example<SheetMusic> example = Example.of(sheetMusic, matcher);
        List<SheetMusic> sheetMusics = sheetMusicRepository.findAll(example);
        return sheetMusics;
    }


}