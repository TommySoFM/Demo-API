package web_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web_demo.entity.Authorities;
import web_demo.entity.Users;
import web_demo.repository.AuthoritiesRepository;
import web_demo.repository.UsersRepository;

@RestController
@RequestMapping("/demo")
public class MainController {
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @PostMapping("/add")
    public @ResponseBody String addNewUser (@RequestParam String username, @RequestParam String password){
        String passwordFormat = "(?=.*?[0-9])(?=.*?[a-zA-Z]).{6,12}";
        if( password.matches(passwordFormat)){
            Users users = new Users();
            users.setUsername(username);
            users.setPassword(password);
            users.setEnabled(1);
            userRepository.save(users);

            Authorities auth = new Authorities();
            auth.setUsername(username);
            auth.setAuthority("ROLE_USER");
            authoritiesRepository.save(auth);
            return "Saved";
        } else {
            return "Saving Failed -- Incorrect Password Format";
        }
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Users> getAllUsers() {
        return userRepository.findAll();
    }
}
