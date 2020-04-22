package server.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.entity.SheetMusic;
import server.repository.SheetMusicRepository;

import java.util.List;
import java.util.Optional;

@Component
public class SheetMusicLogic {
    @Autowired
    SheetMusicRepository sheetMusicRepository;

    public List<SheetMusic> findAll(){
        return sheetMusicRepository.findAll();
    }

    public Optional<SheetMusic> findById(int id){
        return sheetMusicRepository.findById(id);
    }

    public SheetMusic addSheetMusic(String title, String componist, String key, String instrument, String fileName){
        SheetMusic sheetMusic = new SheetMusic(title,componist,key,instrument,fileName);

        return null;

    }
}
