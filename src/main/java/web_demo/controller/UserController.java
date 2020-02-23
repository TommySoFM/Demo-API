package web_demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web_demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addNewUser (String username, String password){
        if (! userService.isUsernameValid(username)){
            return new ResponseEntity<String>("Invalid username", HttpStatus.FORBIDDEN);
        }else if (! userService.isPasswordValid(password)){
            return new ResponseEntity<String>("Invalid Password", HttpStatus.FORBIDDEN);
        }else if( userService.isUsernameUsed(username)){
            return new ResponseEntity<String>("Username is used", HttpStatus.FORBIDDEN);
        }

        userService.saveUser(username, password);
        userService.saveAuthority(username);
         return new ResponseEntity<String>("Sign-up Success!", HttpStatus.ACCEPTED);
        }

    @PostMapping("/isNameUsed")
    public ResponseEntity<String> isNameUsed (String username){
        String isNameUsed = String.valueOf(userService.isUsernameUsed(username));
        return new ResponseEntity<String>(isNameUsed, HttpStatus.ACCEPTED);
    }
}
