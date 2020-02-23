package web_demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthenticationService.class)
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;


    @Test
    @WithMockUser(username = "TommySo")
    public void testGetSessionData(){
        Map<String, String> sessionData = authenticationService.getSessionData();

        assertThat(sessionData.get("username")).isEqualTo("TommySo");
        assertThat(sessionData.get("sessionId")).isEqualTo("1");
        //MockUser Session Id = 1
    }
}
