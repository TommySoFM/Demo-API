package web_demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import web_demo.service.UserService;

import javax.validation.constraints.NotNull;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureTestDatabase
public class UserControllerTest {

    private MockMvc mvc;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.standaloneSetup(this.userController).build();
    }

    @Test
    public void testAddNewUser() throws Exception {
        String username = "TommySo";
        String password = "Abc123!=";
        given(userService.isUsernameValid(username)).willReturn(true);
        given(userService.isUsernameUsed(username)).willReturn(false);
        given(userService.isPasswordValid(password)).willReturn(true);

        mvc.perform(post("/user/add")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", username)
            .param("password", password))
         .andExpect(status().isAccepted());
    }

    @Test
    public void testIsNameUsed() throws Exception {
        given(userService.isUsernameUsed("TommySo")).willReturn(true);
        given(userService.isUsernameUsed("Amy")).willReturn(false);

        String isTommySoUsed = mvcResult_testIsNameUsed("TommySo");
        assertEquals("true", isTommySoUsed);
        String isAmyUsed = mvcResult_testIsNameUsed("Amy");
        assertEquals("false", isAmyUsed);
    }

    @NotNull
    private String mvcResult_testIsNameUsed(String username) throws Exception {
        MvcResult mvcResult = mvc.perform(post("/user/isNameUsed")
                             .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                             .param("username", username))
                             .andExpect(status().isAccepted())
                             .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        return response;
    }

    @MockBean
    private OAuth2AuthorizedClientService oauth2Service;

    @MockBean
    private ClientRegistrationRepository oauth2Repository;
}
