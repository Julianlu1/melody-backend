package server.logic;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import server.controller.SheetMusicController;
import server.entity.SheetMusic;
import server.model.UserTest;
import server.repository.SheetMusicRepository;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(myController.class) ondersteunt niet @Component, @Service of @Repository, daarom heb je @SpringBootTest en @AutoConfigureMockMvc nodig

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SheetMusicControllerIntegrationTest {
    // Test
    @Autowired
    private MockMvc mvc;

    private Gson gson = new Gson();

    private String token;

    @Before
    public void setUp() throws Exception {
        UserTest userTest = new UserTest("admin","admin");

        MockHttpServletRequestBuilder request = post("/authenticate");
        request.content(gson.toJson(userTest));
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mvc.perform(request)
                .andExpect(status().isOk()).andReturn();

        String body = mvcResult.getResponse().getContentAsString();

        // Token van de user wordt opgeslagen in user
        UserTest user = gson.fromJson(body,UserTest.class);
        this.token = user.getToken();
    }

    @Test
    public void getAllSheetmusic() throws Exception
    {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get("/sheetmusic")
                .accept(MediaType.APPLICATION_JSON)
                .header("token", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//                .andExpect(MockMvcResultMatchers.jsonPath("content").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.sheetmusic[*].id").isNotEmpty());

        String body = mvcResult.getResponse().getContentAsString();

        SheetMusic[] sheetMusicArray = gson.fromJson(body,SheetMusic[].class);
        List<SheetMusic> sheetMusics = Arrays.asList(sheetMusicArray);

        // Assert
        assertNotNull(sheetMusics);
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void getSingleSheetmusicTest() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get("/sheetmusic/43")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        SheetMusic sheetMusic = gson.fromJson(body,SheetMusic.class);

        // Assert
        assertNotNull(sheetMusic);
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void filterSheetOnInstrumentTest() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
        .get("/sheetmusic/filter?instrument=Piano")
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();

        SheetMusic[] sheetMusicArray = gson.fromJson(body,SheetMusic[].class);
        List<SheetMusic> sheetMusics = Arrays.asList(sheetMusicArray);
//
//        for(SheetMusic sheet : sheetMusics){
//            assertEquals(sheet.getInstrument(),"Piano");
//        }

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void filterSheetOnComponistAndInstrumentTest() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get("/sheetmusic/filter?componist=Alan Walker&instrument=Piano")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();

        SheetMusic[] sheetMusicArray = gson.fromJson(body,SheetMusic[].class);
        List<SheetMusic> sheetMusics = Arrays.asList(sheetMusicArray);

//        for(SheetMusic sheet : sheetMusics){
//            assertEquals(sheet.getComponist(),"Alan Walker");
//            assertEquals(sheet.getInstrument(),"Piano");
//        }

        assertEquals(200,mvcResult.getResponse().getStatus());
    }
}
