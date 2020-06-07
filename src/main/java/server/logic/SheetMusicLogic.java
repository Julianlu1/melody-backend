package server.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import server.entity.Instrument;
import server.entity.PlayedSheet;
import server.entity.SheetMusic;
import server.entity.User;
import server.repository.InstrumentRepository;
import server.repository.SheetMusicRepository;
import server.repository.UserRepository;

import java.io.File;
import java.io.IOException;
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
    FileService fileService;


    public List<SheetMusic> findAll(){
        return sheetMusicRepository.findAll();
    }

    public SheetMusic findById(int id){
        return sheetMusicRepository.findById(id).orElse(null);
    }

    public SheetMusic addSheetMusic(String title, String componist, String key, String fileName, MultipartFile file, Instrument instrument) throws IOException {
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

    public List<SheetMusic> findSheetMusicByFilter(String componist, String key, Integer instrument_id){

        
        Instrument instrument = instrumentRepository.findById(instrument_id).orElse(null);
        SheetMusic sheetMusic = new SheetMusic(componist,key,instrument);

        Example<SheetMusic> employeeExample = Example.of(sheetMusic, ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("title")
                .withIgnorePaths("pdf")
                .withIgnorePaths("comments")
        .withIgnorePaths("instrument_id"));

        List<SheetMusic> sheetMusics = sheetMusicRepository.findAll(employeeExample);
        return sheetMusics;
    }

    public SheetMusic markAsPlayed(User user,int sheetId){
        SheetMusic sheetMusic = sheetMusicRepository.findById(sheetId).orElse(null);
        PlayedSheet playedSheet = new PlayedSheet(sheetMusic,user);
//        user.getPlayedSheets().add(playedSheet);
        userRepository.save(user);
        return sheetMusic;
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
