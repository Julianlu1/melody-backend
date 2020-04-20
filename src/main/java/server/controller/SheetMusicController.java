package server.controller;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import server.entity.SheetMusic;
import server.logic.FileService;
import server.repository.SheetMusicRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
    ServletContext context;
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
        ClassPathResource cpr = new ClassPathResource("static");
        InputStream is = cpr.getInputStream();
        String result = IOUtils.toString(is);
        fileService.uploadFile(file,result);

//        Resource resource = resourceLoader.getResource("classpath:static");
//        InputStream inputStream = resource.getInputStream();
//        String result2 = IOUtils.toString(inputStream);
//        fileService.uploadFile(file,result2);

        SheetMusic sheetMusic = new SheetMusic(title,componist,key,instrument,file.getOriginalFilename());
        return sheetMusicRepository.save(sheetMusic);
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