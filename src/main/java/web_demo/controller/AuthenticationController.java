package web_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web_demo.service.AuthenticationService;

import java.util.Map;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping("/loginSuccess")
    public ResponseEntity<String> loginSuccess (){
        return new ResponseEntity<String>("Login Success", HttpStatus.OK);
    }

    @RequestMapping("/loginFailed")
    public ResponseEntity<String> loginFailure() {
        return new ResponseEntity<String>("Login Failed", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/isSessionValid")
    public ResponseEntity<Map<String, String>> isSessionValid() {
        if (authenticationService.isSessionValid()){
            Map<String, String> sessionData = authenticationService.getSessionData();
            return new ResponseEntity<Map<String, String>>(sessionData, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
