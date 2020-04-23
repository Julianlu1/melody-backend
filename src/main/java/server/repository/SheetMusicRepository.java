package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import server.entity.SheetMusic;

import java.util.List;

@Repository
public interface SheetMusicRepository extends JpaRepository<SheetMusic,Integer>, QueryByExampleExecutor<SheetMusic> {
    List<SheetMusic> findByComponistAndKeyAndInstrument(String componist, String key, String instrument);
}
