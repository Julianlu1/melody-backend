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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import server.entity.User;
import server.model.UserTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private Gson gson = new Gson();
    @Test
    public void loginWithAdminTest() throws Exception {
        // Arrange
//        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));

//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
//        .post("/authenticate")
//                .content("{\"username\":admin,\"password\":admin}"))
//                .andReturn();
//
//                String body = mvcResult.getResponse().getContentAsString();
//
//        User user = gson.fromJson(body,User.class);

        UserTest userTest = new UserTest("admin","password");

        MockHttpServletRequestBuilder request = post("/authenticate");
        request.content(gson.toJson(userTest));
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(status().isOk());

        assertNotNull("user");
    }
}
