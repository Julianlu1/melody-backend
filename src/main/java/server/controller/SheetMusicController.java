package server.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import server.entity.SheetMusic;
import server.logic.FileService;
import server.repository.SheetMusicRepository;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@RestController
public class SheetMusicController {
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    FileService fileService;

    @Autowired
    ServletContext servletContext;

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
        String fileName = file.getOriginalFilename();
        String filePath = ResourceUtils.getFile("classpath:pdf").toString();

        URL s = SheetMusicController.class
                .getClassLoader().getResource("pdf/" + filePath);
        fileService.uploadFile(file,filePath);

//
        byte [] pdf = file.getBytes();
//
//        // pdf uploaden naar resources/pdf
//        // File path

        SheetMusic sheetMusic = new SheetMusic(title,componist,key,instrument,fileName);
        return sheetMusicRepository.save(sheetMusic);
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

    @GetMapping("sheetmusic/getFaded")
    public File getSheet() throws IOException {


        Resource resource = new ClassPathResource("pdf/Faded.pdf");
        InputStream input = resource.getInputStream();
        File fiile = resource.getFile();

        return fiile;
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