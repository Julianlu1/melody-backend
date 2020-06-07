package server.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.entity.Instrument;
import server.repository.InstrumentRepository;

import java.util.List;

@Component
public class InstrumentLogic {

    @Autowired
    private InstrumentRepository instrumentRepository;

    public List<Instrument> findAll() {
        return this.instrumentRepository.findAll();
    }

    public Instrument findById(int id){
        return this.instrumentRepository.findById(id).orElse(null);
    }
}
