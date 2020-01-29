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
@RequestMapping("/demo")
public class MainController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    private final UserService userService;

    public MainController(UserService usersService) {
        this.userService = usersService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewUser (String username, String password){
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
         return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        }

    @GetMapping("/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        logger.debug(String.valueOf(userRepository.findAll()));
        return userRepository.findAll();
    }
}
