package server.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import server.entity.SheetMusic;
import server.repository.SheetMusicRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class SheetMusicLogic {
    @Autowired
    SheetMusicRepository sheetMusicRepository;

    @Autowired
    FileService fileService;


    public List<SheetMusic> findAll(){
        return sheetMusicRepository.findAll();
    }

    public Optional<SheetMusic> findById(int id){
        return sheetMusicRepository.findById(id);
    }

    public SheetMusic addSheetMusic(String title, String componist, String key, String instrument, String fileName, MultipartFile file) throws IOException {
        // Aanmaken
        SheetMusic sheetMusic = new SheetMusic(title,componist,key,instrument,fileName);

        // Bestand opslaan op server
        String path = new File(".").getCanonicalPath() + "/src/main/webapp/WEB-INF/images/";
        fileService.uploadFile(file,path);

        // Sheetmusic toevoegen aan database
        sheetMusicRepository.save(sheetMusic);

        // Returnen
        return sheetMusic;
    }
}
