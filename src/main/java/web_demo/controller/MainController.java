package web_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web_demo.entity.Authorities;
import web_demo.entity.Users;
import web_demo.repository.AuthoritiesRepository;
import web_demo.repository.UsersRepository;
import web_demo.service.UsersService;

@RestController
@RequestMapping("/demo")
public class MainController {
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    private final UsersService usersService;

    public MainController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/add")
    public @ResponseBody String addNewUser (String username, String password){
//        if (! usersService.isUsernameValid(username)){
//            return "Username is invalid!";
//        }
//
//        if (! usersService.isPasswordValid(password)){
//            return "Password is invalid!";
//        }

         Users users = usersService.initUser(username, password);
         userRepository.save(users);

         Authorities auth = usersService.initAuthority(username);
         authoritiesRepository.save(auth);
         return "Saved";
        }

    @GetMapping("/all")
    public @ResponseBody Iterable<Users> getAllUsers() {
        return userRepository.findAll();
    }
}
