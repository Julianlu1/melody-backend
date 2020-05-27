package server.logic;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import server.entity.Comment;
import server.entity.SheetMusic;
import server.entity.User;
import server.model.CommentModel;
import server.model.UserTest;
import server.repository.SheetMusicRepository;
import server.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private SheetMusicRepository sheetMusicRepository;

    @Autowired
    private UserRepository userRepository;

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
    public void addCommentTest() throws Exception {
//        boolean foundComment = false;

        CommentModel comment = new CommentModel(66,"test description", 8.0);

        MvcResult mvcResult = mvc.perform( MockMvcRequestBuilders
                .post("/comments")
                .header("Authorization", "Bearer " + this.token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(comment)))
                .andExpect(status().isOk()).andReturn();

        String body = mvcResult.getResponse().getContentAsString();

//        CommentModel[] commentArray = gson.fromJson(body,CommentModel[].class);
//        for(CommentModel c : commentArray){
//            if(c.getDescription().equals("test description")){
//                foundComment = true;
//            }
//        }
//        assertTrue(foundComment);
        assertEquals(200,mvcResult.getResponse().getStatus());
    }
}

