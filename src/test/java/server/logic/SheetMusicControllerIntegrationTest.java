package server.logic;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import server.controller.SheetMusicController;
import server.entity.Instrument;
import server.entity.SheetMusic;
import server.model.SheetMusicModel;
import server.model.UserTest;
import server.repository.InstrumentRepository;
import server.repository.SheetMusicRepository;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(myController.class) ondersteunt niet @Component, @Service of @Repository, daarom heb je @SpringBootTest en @AutoConfigureMockMvc nodig
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc

@ExtendWith(MockitoExtension.class)
public class SheetMusicControllerIntegrationTest {

    @InjectMocks
    private SheetMusicController sheetMusicController;

    @Mock
    private SheetMusicLogic sheetMusicLogic;

    @Mock
    private InstrumentLogic instrumentLogic;



    private MockMultipartFile mockFile;

    @Before
    public void init() throws IOException {
        MockitoAnnotations.initMocks(this);

        mockFile =
                new MockMultipartFile(
                        "newTitel",
                        "newTitel.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));


        Instrument instrument = new Instrument("Piano");
        SheetMusic sheetMusic = new SheetMusic("titel","componist","c","titel",instrument);

        SheetMusic newSheetMusic = new SheetMusic("newTitel","NewComponist","c","newTitel",instrument);

        // Wanneer findById wordt uitgevoerd, returned hij sheetmusic (bij de testmethode findSheetmusicById)
        lenient().when(sheetMusicLogic.findById(1)).thenReturn(sheetMusic);

        // Wanneer addSheetmusic wordt uitgevoerd, returned hij newSheetMusic
        lenient().when(sheetMusicLogic.addSheetMusic("newTitel","newComponist","c","newTitel.pdf",mockFile,instrument)).thenReturn(newSheetMusic);
        lenient().when(instrumentLogic.findById(1)).thenReturn(instrument);

        // Wanneer er gefilterd wordt op piano, return dit
        List<SheetMusic> sheetMusics = new ArrayList<>();
        sheetMusics.add(new SheetMusic("River Flows In You","Yiruma","c","River Flows In You",instrument));
        sheetMusics.add(new SheetMusic("River Flows In You","Yiruma","a","River Flows In You",instrument));

        lenient().when(sheetMusicLogic.findSheetMusicByFilter("Yiruma",null,null)).thenReturn(sheetMusics);
    }

    @Test
    public void findAllSheetmusic(){
        HttpStatus status = sheetMusicController.findAll().getStatusCode();
        assertEquals(200,status.value());
    }

    @Test
    public void findSheetmusicById(){
        ResponseEntity response = sheetMusicController.findById("1");
        HttpStatus status = response.getStatusCode();
        assertEquals(200,status.value());
    }

    @Test
    public void findSheetmusicByIncorrectId(){
        ResponseEntity response = sheetMusicController.findById("500");
        HttpStatus status = response.getStatusCode();
        assertEquals(400,status.value());
    }


    @Test
    public void addSheetMusic() throws IOException {
        // "newTitel","newComponist","c","newTitel",mockFile,instrument
        ResponseEntity response = sheetMusicController.create(mockFile,"newTitel","newComponist","c",1);
        HttpStatus status = response.getStatusCode();
        assertEquals(200,status.value());
    }

    @Test
    public void addSheetMusicWrongFileType() throws IOException {
    MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "file",
            "photo.jpeg",
            MediaType.IMAGE_JPEG_VALUE,
            "<<image>>".getBytes(StandardCharsets.UTF_8));
             ResponseEntity response = sheetMusicController.create(mockMultipartFile,"FouteFile","TestComponist","c",1);
        HttpStatus status = response.getStatusCode();
        assertEquals(400,status.value());
    }

    @Test
    public void deleteSheetMusic(){
        ResponseEntity response = sheetMusicController.deleteById("2");
        HttpStatus status = response.getStatusCode();
        assertEquals(200,status.value());
    }

    @Test
    public void deleteAll(){
        ResponseEntity response = sheetMusicController.deleteAll();
        HttpStatus status = response.getStatusCode();
        assertEquals(200,status.value());
    }

    @Test
    public void filterByComponist(){
        ResponseEntity response = sheetMusicController.findSheetMusicByFilter("Yiruma",null,null);
        HttpStatus status = response.getStatusCode();
        assertEquals(200,status.value());
    }


//    // Test
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private InstrumentRepository instrumentRepository;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    private Gson gson = new Gson();
//
//    private String token;
//
//    @Before
//    public void setUp() throws Exception {
//        UserTest userTest = new UserTest("admin","admin");
//
//        MockHttpServletRequestBuilder request = post("/authenticate");
//        request.content(gson.toJson(userTest));
//        request.accept(MediaType.APPLICATION_JSON);
//        request.contentType(MediaType.APPLICATION_JSON);
//        MvcResult mvcResult = mvc.perform(request)
//                .andExpect(status().isOk()).andReturn();
//
//        String body = mvcResult.getResponse().getContentAsString();
//
//        // Token van de user wordt opgeslagen in user
//        UserTest user = gson.fromJson(body,UserTest.class);
//        this.token = user.getToken();
//    }
//
//    @Test
//    public void addSheetMusic() throws Exception {
//        Instrument instrument = instrumentRepository.findById(1).orElse(null);
//        SheetMusic sheetMusic = new SheetMusic("testsheet","testcomponist","c","test_sheet",instrument);
//        MockMultipartFile file =
//                new MockMultipartFile(
//                        "file",
//                        "test_sheet.pdf",
//                        MediaType.APPLICATION_PDF_VALUE,
//                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));
//
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.multipart("/sheetmusic")
//        .file(file)
//        .param("title","testsheet")
//        .param("componist","testcomponist")
//        .param("key","c")
//        .param("instrument_id","1"))
//                .andExpect(status().isOk())
//                .andReturn();
////        .andExpect(content().string("success"));
//
//        String body = mvcResult.getResponse().getContentAsString();
//        SheetMusicModel postedSheetmusic = gson.fromJson(body,SheetMusicModel.class);
//
//        assertEquals(200,mvcResult.getResponse().getStatus());
//        assertEquals("testsheet",postedSheetmusic.getTitle());
//        assertEquals("Piano",postedSheetmusic.getInstrument_Description());
//        assertEquals("test_sheet.pdf",postedSheetmusic.getPdf());
//    }
//
//    @Test
//    public void getAllSheetmusic() throws Exception
//    {
//        boolean foundTestsheet = false;
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
//                .get("/sheetmusic")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
////                .andExpect(MockMvcResultMatchers.jsonPath("content").exists())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.sheetmusic[*].id").isNotEmpty());
//
//        String body = mvcResult.getResponse().getContentAsString();
//
//        SheetMusic[] sheetMusicArray = gson.fromJson(body,SheetMusic[].class);
//        List<SheetMusic> sheetMusics = Arrays.asList(sheetMusicArray);
//
//        // Als er een sheetmusic bestaat met de titel testsheet, wordt de boolean op true gezet
//        for(SheetMusic sheetMusic : sheetMusics){
//            if(sheetMusic.getTitle().equals("testsheet")){
//                foundTestsheet = true;
//            }
//        }
//
//        // Assert
//        assertNotNull(sheetMusics);
//        assertTrue(foundTestsheet);
//        assertEquals(200,mvcResult.getResponse().getStatus());
//    }
//
//    @Test
//    public void getSingleSheetmusicTest() throws Exception {
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
//                .get("/sheetmusic/59")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String body = mvcResult.getResponse().getContentAsString();
//        SheetMusicModel sheetMusic = gson.fromJson(body,SheetMusicModel.class);
//
//        // Assert
//        assertNotNull(sheetMusic);
//        assertEquals("To The Moon Having Lived",sheetMusic.getTitle());
//        assertEquals("Kan Gao", sheetMusic.getComponist());
//        assertEquals("c", sheetMusic.getKey());
//        assertEquals("To_The_Moon_Having_Lived.pdf", sheetMusic.getPdf());
//        assertEquals("Piano", sheetMusic.getInstrument_Description());
//
//        assertEquals(200,mvcResult.getResponse().getStatus());
//    }
//
//    @Test
//    public void filterSheetOnInstrumentTest() throws Exception {
//        // Als deze list alleen 'true' bevat moet de test slagen
//        List<Boolean> foundInstrument = new ArrayList<>();
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
//        .get("/sheetmusic/filter?instrument_id=1")
//        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String body = mvcResult.getResponse().getContentAsString();
//
//        SheetMusicModel[] sheetMusicArray = gson.fromJson(body,SheetMusicModel[].class);
//        List<SheetMusicModel> sheetMusics = Arrays.asList(sheetMusicArray);
//
//        for(int i = 0; i < sheetMusics.size(); i++){
//            if(sheetMusics.get(i).getInstrument_Description().equals("Piano")){
//                foundInstrument.add(true);
//            }else{
//                foundInstrument.add(false);
//            }
//        }
//        assertFalse(foundInstrument.contains(false)); // Als .contains true returned, is het niet goed. Het moet false returen
//        assertEquals(200,mvcResult.getResponse().getStatus());
//    }
//
//    @Test
//    public void filterSheetOnComponistAndInstrumentTest() throws Exception {
//        List<Boolean> foundComponistAndInstrument = new ArrayList<>();
//
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
//                .get("/sheetmusic/filter?componist=Alan Walker&instrument_id=1")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String body = mvcResult.getResponse().getContentAsString();
//
//        SheetMusicModel[] sheetMusicArray = gson.fromJson(body,SheetMusicModel[].class);
//        List<SheetMusicModel> sheetMusics = Arrays.asList(sheetMusicArray);
//
//        for(int i =0; i < sheetMusics.size(); i++){
//            if(sheetMusics.get(i).getComponist().equals("Alan Walker") && sheetMusics.get(i).getInstrument_Description().equals("Piano")){
//                foundComponistAndInstrument.add(true);
//            }else{
//                foundComponistAndInstrument.add(false);
//            }
//        }
//        assertFalse(foundComponistAndInstrument.contains(false));
//        assertEquals(200,mvcResult.getResponse().getStatus());
//    }
}
