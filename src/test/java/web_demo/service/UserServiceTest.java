package web_demo.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import web_demo.entity.Authority;
import web_demo.entity.User;
import web_demo.repository.AuthorityRepository;
import web_demo.repository.UserRepository;
import web_demo.service.UserService;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserService.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    AuthorityRepository authorityRepository;

    // RegEx: (?=.*?[a-zA-Z\\W]).{6,20}
    @Test
    public void testIsUsernameValid(){
        assertTrue(userService.isUsernameValid("TommySo"));

        assertFalse(userService.isUsernameValid("Tommy"));      //Too Short
        assertFalse(userService.isUsernameValid("1234567"));    //All Numbers
    }

    // RegEx: (?=.*?[a-z])(?=.*?[A-Z])(?=.*?[\\W]).{8,25}
    @Test
    public void testIsPasswordValid(){
        assertTrue(userService.isPasswordValid("Abc123!="));

        assertFalse(userService.isPasswordValid("Ab12!"));      //Too Short
        assertFalse(userService.isPasswordValid("abc123!="));   //No Capital Letter
        assertFalse(userService.isPasswordValid("Abc123de"));   //No Special Character
        assertFalse(userService.isPasswordValid("12345678"));   //All Numbers
    }

    @Test
    public void testIsUsernameUsed(){
        when(userRepository.existsUserByUsernameEquals("TommySo")).thenReturn(true);
        when(userRepository.existsUserByUsernameEquals("Tommy123")).thenReturn(false);

        assertTrue(userService.isUsernameUsed("TommySo"));
        assertFalse(userService.isUsernameUsed("Tommy123"));
    }
}
