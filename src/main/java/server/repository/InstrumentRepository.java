package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entity.Instrument;

public interface InstrumentRepository extends JpaRepository<Instrument , Integer> {

}
