package server.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.entity.Instrument;
import server.entity.User;
import server.repository.InstrumentRepository;
import server.repository.UserRepository;

import java.util.List;

@Component
public class UserLogic {

    @Autowired
    private UserRepository userRepository;


    public User findById(int id){
        return this.userRepository.findById(id).orElse(null);
    }
}
