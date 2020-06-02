package server.logic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import server.controller.InstrumentController;
import server.entity.Instrument;
import server.repository.InstrumentRepository;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class InstrumentIntegrationTest {

    @InjectMocks
    private InstrumentController instrumentController;

    @Mock
    private InstrumentLogic instrumentLogic;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findInstruments(){
        HttpStatus status =instrumentController.getAllInstruments().getStatusCode();
        assertEquals(200,status.value());
    }

//    @Test
//    public void getInstruments() throws Exception {
//        MockHttpServletRequestBuilder request = get("/instrument");
//
//        MvcResult mvcResult = mvc.perform(request).andExpect(status().isOk()).andReturn();
//
//        String body = mvcResult.getResponse().getContentAsString();
//
//        Instrument[] instruments = gson.fromJson(body,Instrument[].class);
//
//        assertNotNull(instruments);
//        assertEquals("Piano",instruments[0].getDescription());
//        assertEquals("Gitaar",instruments[1].getDescription());
//        assertEquals("Viool",instruments[2].getDescription());
//        assertEquals("Drum",instruments[3].getDescription());
//    }
}
