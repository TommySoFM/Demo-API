package web_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import web_demo.repository.UserRepository;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/loginSuccess")
    public ResponseEntity<Map<String, String>> successController (Principal principal){
        String principalUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        String principalUserId = String.valueOf(userRepository.findByUsername(principalUsername).getId());
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();

        Map<String, String> userData = new HashMap<>();
        userData.put("username", principalUsername);
        userData.put("userId", principalUserId);
        userData.put("sessionId", sessionId);
        return new ResponseEntity<Map<String, String>>(userData, HttpStatus.OK);
    }

    @RequestMapping("/loginFailed")
    public Map<String, String> failureController (HttpServletRequest request, HttpServletResponse response){
        Map<String, String> message = new HashMap<>();
        message.put("status", "403");
        message.put("message", "Login Failed");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        return message;
        //or ==> return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/isSessionValid")
    public void isSessionValid(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getName().equals("anonymousUser")){
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
