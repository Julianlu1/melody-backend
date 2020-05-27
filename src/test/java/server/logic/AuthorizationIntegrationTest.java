package server.logic;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import server.controller.JwtAuthenticationController;
import server.entity.User;
import server.model.UserTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class AuthorizationIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private Gson gson = new Gson();

    @Test
    public void registerTest() throws Exception {
        UserTest userTest = new UserTest("peter","peter");

        MockHttpServletRequestBuilder request = post("/register");
        request.content(gson.toJson(userTest));
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mvc.perform(request)
                .andExpect(status().isOk()).andReturn();

        String body = mvcResult.getResponse().getContentAsString();

        assertNotNull(body);
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void loginWithAdminTest() throws Exception {
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

        assertNotNull(user.getToken());
    }
}
