package server.logic;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import server.entity.Instrument;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InstrumentIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private Gson gson = new Gson();

    @Test
    public void getInstruments() throws Exception {
        MockHttpServletRequestBuilder request = get("/instrument");

        MvcResult mvcResult = mvc.perform(request).andExpect(status().isOk()).andReturn();

        String body = mvcResult.getResponse().getContentAsString();

        Instrument[] instruments = gson.fromJson(body,Instrument[].class);

        assertNotNull(instruments);
        assertEquals("Piano",instruments[0].getDescription());
        assertEquals("Gitaar",instruments[1].getDescription());
        assertEquals("Viool",instruments[2].getDescription());
        assertEquals("Drum",instruments[3].getDescription());

//        assertTrue(true);
    }
}
