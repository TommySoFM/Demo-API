package web_demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web_demo.entity.Authority;
import web_demo.entity.User;
import web_demo.repository.AuthorityRepository;
import web_demo.repository.UserRepository;
import web_demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    private final UserService userService;

    public UserController(UserService usersService) {
        this.userService = usersService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewUser (String username, String password){
        if (! userService.isUsernameValid(username)){
            throw new CustomException("Invalid username");
        }else if (! userService.isPasswordValid(password)){
            throw new CustomException("Invalid Password");
        }else if( userService.isUsernameUsed(username)){
            throw new CustomException("Username is used");
        }

        User user = userService.initUser(username, password);
        userRepository.save(user);

        Authority auth = userService.initAuthority(username);
        authorityRepository.save(auth);
         return new ResponseEntity<String>("Sign-up Success!", HttpStatus.ACCEPTED);
        }

    @PostMapping("/isNameUsed")
    public ResponseEntity<String> isNameUsed (String username){
        String isNameUsed;
        if(userService.isUsernameUsed(username)){
            isNameUsed = "true";
        }else{
            isNameUsed = "false";
        }
        return new ResponseEntity<String>(isNameUsed, HttpStatus.ACCEPTED);
    }
}
