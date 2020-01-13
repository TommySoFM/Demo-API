package web_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web_demo.entity.User;
import web_demo.repository.UserRepository;

@RestController
@RequestMapping("/demo")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String password, @RequestParam String email){
        String passwordFormat = "(?=.*?[0-9])(?=.*?[a-zA-Z]).{6,12}";
        if( password.matches(passwordFormat)){
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            user.setEmail(email);
            userRepository.save(user);
            return "Saved";
        } else {
            return "Saving Failed -- Incorrect Password Format";
        }
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
