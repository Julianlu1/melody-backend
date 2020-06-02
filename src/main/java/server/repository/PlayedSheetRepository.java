package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import server.entity.PlayedSheet;

import javax.persistence.NamedNativeQuery;
import java.util.List;

@Repository
public interface PlayedSheetRepository extends JpaRepository<PlayedSheet, Integer> {
    List<PlayedSheet> findByUserId(int userId);
    @Query(value = "DELETE FROM played_sheet WHERE user_id =  ?1 AND sheet_music_id = ?2", nativeQuery = true)
    void deleteByUserIdAndSheetMusicId(int userId,int sheetMusicId);


}
