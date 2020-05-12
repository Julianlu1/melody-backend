package server.logic;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import server.entity.Comment;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    private Gson gson;

//    @Test
//    public void addCommentTest() throws Exception {
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
//                .get("/comments/45")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn();
//
//        String body = mvcResult.getResponse().getContentAsString();
//
//        Comment[] commentArray = gson.fromJson(body,Comment[].class);
//
//        assertNotNull(commentArray);
//        assertEquals(200,mvcResult.getResponse().getStatus());
//    }

}
