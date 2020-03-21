package server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import server.entity.Comment;
import server.entity.SheetMusic;
import server.repository.SheetMusicRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class SheetMusicController {

    @Autowired
    SheetMusicRepository sheetMusicRepository;

    @GetMapping("/sheetmusic")
    public List<SheetMusic> index(){
        return sheetMusicRepository.findAll();
    }

    @GetMapping("/sheetmusic/{id}")
    public SheetMusic getById(@PathVariable String id){
        int sheetId = Integer.parseInt(id);
        SheetMusic sheetMusic = sheetMusicRepository.findById(sheetId).orElse(null);
        return sheetMusic;
    }

    @PostMapping(value = "/sheetmusic")
    public SheetMusic create(@RequestParam("file") MultipartFile file, @RequestParam("title") String title, @RequestParam("componist") String componist, @RequestParam("key") String key, @RequestParam("instrument") String instrument) throws IOException {
        byte [] pdf = file.getBytes();

        SheetMusic sheetMusic = new SheetMusic(title,componist,key,instrument,pdf);
        return sheetMusicRepository.save(sheetMusic);
    }


}