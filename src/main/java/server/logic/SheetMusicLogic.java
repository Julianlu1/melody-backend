package server.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import server.entity.*;
import server.repository.InstrumentRepository;
import server.repository.PlayedSheetRepository;
import server.repository.SheetMusicRepository;
import server.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class SheetMusicLogic {
    @Autowired
    SheetMusicRepository sheetMusicRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InstrumentRepository instrumentRepository;

    @Autowired
    PlayedSheetRepository playedSheetRepository;

    @Autowired
    FileService fileService;

    private static final List<String> contentTypes = Arrays.asList("application/pdf");

    public List<SheetMusic> findAll(){
        return sheetMusicRepository.findAll();
    }

    public SheetMusic findById(int id){
        return sheetMusicRepository.findById(id).orElse(null);
    }

    public SheetMusic addSheetMusic(String title, String componist, String key, String fileName, MultipartFile file, Instrument instrument) throws IOException {
        String fileContentType = file.getContentType();

        if(fileContentType.equals("application/pdf")){
            // Aanmaken
            SheetMusic sheetMusic = new SheetMusic(title,componist,key,fileName,instrument);
            // Bestand opslaan op server
            String path = new File(".").getCanonicalPath() + "/src/main/webapp/WEB-INF/images/";
            fileService.uploadFile(file,path);

            // Sheetmusic toevoegen aan database
            sheetMusicRepository.save(sheetMusic);

            // Returnen
            return sheetMusic;
        }
        return null;
    }

    public List<SheetMusic> findSheetMusicByFilter(String componist, String key, Integer instrument_id){

        SheetMusic sheetMusic;
        if(instrument_id!=null){
            Instrument instrument = instrumentRepository.findById(instrument_id).orElse(null);
            sheetMusic = new SheetMusic(componist,key,instrument);
        }else{
            sheetMusic = new SheetMusic(componist,key);
        }


        Example<SheetMusic> employeeExample = Example.of(sheetMusic, ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("title")
                .withIgnorePaths("pdf")
                .withIgnorePaths("comments")
        .withIgnorePaths("instrument_id"));

        List<SheetMusic> sheetMusics = sheetMusicRepository.findAll(employeeExample);
        return sheetMusics;
    }



    public void deleteById(int id) throws IOException {
        SheetMusic sheetMusic = findById(id);
        String filePath = new File(".").getCanonicalPath() + "/src/main/webapp/WEB-INF/images/" + sheetMusic.getPdf();

        fileService.deleteFile(filePath);
        sheetMusicRepository.deleteById(id);
    }
    public void deleteAll(){
        List<SheetMusic> sheetMusics = findAll();
        sheetMusics.forEach(sheetMusic -> {
            String filePath = null;
            try {
                filePath = new File(".").getCanonicalPath() + "/src/main/webapp/WEB-INF/images/" + sheetMusic.getPdf();
                fileService.deleteFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sheetMusicRepository.deleteAll();
    }


}
