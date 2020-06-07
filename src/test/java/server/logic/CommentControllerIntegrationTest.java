package server.logic;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import server.config.JwtTokenUtil;
import server.controller.CommentController;
import server.entity.Comment;
import server.entity.Instrument;
import server.entity.SheetMusic;
import server.entity.User;
import server.model.CommentModel;
import server.model.UserDetailsTest;
import server.model.UserTest;
import server.repository.SheetMusicRepository;
import server.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class CommentControllerIntegrationTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentLogic commentLogic;

    @Mock
    private SheetMusicLogic sheetMusicLogic;

    @Mock
    private UserLogic userLogic;

    @Mock
    private JwtService jwtService;

    private SheetMusic sheetMusic;
    private User user;
    private Instrument instrument;
    private String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEiLCJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.A1uESZbuhzQrerx1JMQ6AJ6xqM-SKlq1G_3G8jhtZE8";
    private Map<String,String> body;
    private Comment comment;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        instrument = new Instrument("Piano");
        sheetMusic = new SheetMusic("TestSheet","TestComponist","c","TestSheet",instrument);

        user = new User("TestUser","TestPassword");

        comment = new Comment(sheetMusic,user,"Goede muziek",10.0);
        body = new HashMap<>();
        body.put("sheetId","1");
        body.put("description","Goede muziek");
        body.put("score","10.0");

        lenient().when(sheetMusicLogic.findById(1)).thenReturn(sheetMusic); // Return sheetmusic als findbyid is uitgevoerd
        lenient().when(userLogic.findById(0)).thenReturn(user); // Return user wanneer findbyid is uitgevoerd
        lenient().when(commentLogic.AddComment(sheetMusic,user,"Goede muziek",10.0)).thenReturn(comment); // Return comment wanneer findbyid is uitgevoerd
    }

    @Test
    public void addComment(){
        ResponseEntity response = commentController.create(this.token,body);
        HttpStatus status = response.getStatusCode();
        assertEquals(200,status.value());
    }







//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private SheetMusicRepository sheetMusicRepository;
//
//    @Autowired
//    private UserRepository userRepository;
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
//    public void addCommentTest() throws Exception {
////        boolean foundComment = false;
//
//        CommentModel comment = new CommentModel(66,"test description", 8.0);
//
//        MvcResult mvcResult = mvc.perform( MockMvcRequestBuilders
//                .post("/comments")
//                .header("Authorization", "Bearer " + this.token)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(comment)))
//                .andExpect(status().isOk()).andReturn();
//
//        String body = mvcResult.getResponse().getContentAsString();
//
////        CommentModel[] commentArray = gson.fromJson(body,CommentModel[].class);
////        for(CommentModel c : commentArray){
////            if(c.getDescription().equals("test description")){
////                foundComment = true;
////            }
////        }
////        assertTrue(foundComment);
//        assertEquals(200,mvcResult.getResponse().getStatus());
//    }
}

