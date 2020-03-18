package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.SheetMusic;

@Repository
public interface SheetMusicRepository extends JpaRepository<SheetMusic,Integer> {
}
