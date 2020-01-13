package web_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web_demo.entity.User;
import web_demo.repository.UserRepository;

@Controller
@RequestMapping("/demo")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String email){
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
