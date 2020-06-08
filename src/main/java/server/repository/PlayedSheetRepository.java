package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.entity.PlayedSheetEntity;

import java.util.List;

@Repository
public interface PlayedSheetRepository extends JpaRepository<PlayedSheetEntity, Integer> {
    List<PlayedSheetEntity> findByUserId(int userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM played_sheet WHERE user_id =  ?1 AND sheet_music_id = ?2", nativeQuery = true)
    void deleteByUserIdAndSheetMusicId(Integer userId,Integer sheetMusicId);

}
