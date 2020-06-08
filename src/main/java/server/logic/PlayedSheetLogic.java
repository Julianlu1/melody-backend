package server.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import server.entity.PlayedSheetEntity;
import server.entity.SheetMusic;
import server.entity.User;
import server.repository.PlayedSheetRepository;
import server.repository.SheetMusicRepository;
import server.repository.UserRepository;

import java.util.List;

@Component
public class PlayedSheetLogic {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SheetMusicRepository sheetMusicRepository;

    @Autowired
    private PlayedSheetRepository playedSheetRepository;

    public boolean markAsPlayed(Integer sheetId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        SheetMusic sheetMusic = sheetMusicRepository.findById(sheetId).orElse(null);
        PlayedSheetEntity playedSheet = new PlayedSheetEntity(sheetMusic,user);

        List<PlayedSheetEntity> userSheets = playedSheetRepository.findByUserId(user.getId());
        boolean isPlayed = checkIfPlayed(userSheets,sheetId);

        // Als isPlayed true is, is de sheet al gemarkeerd.
        if(isPlayed){
            playedSheetRepository.deleteByUserIdAndSheetMusicId(user.getId(),sheetId);
            return true;
//            playedSheetRepository.delete(playedSheet);
        }else{
            playedSheetRepository.save(playedSheet);
            return false;
        }
    }

    // Als de sheet al is gemarkeerd, return true.
    private boolean checkIfPlayed(List<PlayedSheetEntity> userSheets, int newSheetId){
        for(PlayedSheetEntity userSheet : userSheets){
            if(userSheet.getSheetMusic().getId() == newSheetId){
                return true;
            }
        }
        return false;
    }

}
