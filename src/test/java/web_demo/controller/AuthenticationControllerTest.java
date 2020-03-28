package web_demo.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import web_demo.repository.UserRepository;
import web_demo.service.AuthenticationService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
@AutoConfigureTestDatabase
@WithMockUser(username = "TommySo", password = "Abc123!=")
public class AuthenticationControllerTest {

    private MockMvc mvc;

    @Autowired
    private AuthenticationController authenticationController;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationService authenticationService;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(this.authenticationController).build();
    }

    @Test
    public void testLoginSuccess() throws Exception{
        mvc.perform(get("/loginSuccess"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginFailure() throws Exception{
        mvc.perform(post("/loginFailed"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testIsSessionValid() throws Exception{
        given(authenticationService.isSessionValid()).willReturn(true);

        mvc.perform(get("/isSessionValid"))
                .andExpect(status().isAccepted());
    }

    @MockBean
    private OAuth2AuthorizedClientService oauth2Service;

    @MockBean
    private ClientRegistrationRepository oauth2Repository;
}
