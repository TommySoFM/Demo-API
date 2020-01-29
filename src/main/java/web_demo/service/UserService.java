package web_demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import web_demo.entity.Authority;
import web_demo.entity.User;
import web_demo.repository.UserRepository;


@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //Sign-up Format
    String passwordFormat = "(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[\\W]).{8,25}";
    String usernameFormat = "(?=.*?[a-zA-Z\\W]).{6,20}";

    public boolean isPasswordValid (String password){
        return password.matches(passwordFormat);
    }

    public boolean isUsernameValid (String username){
        return username.matches(usernameFormat);
    }

    //Check if username is used
    public boolean isUsernameUsed (String username){
        User user = userRepository.findByUsername(username);

        if (user == null){
            return false;
        } else {
            return true;
        }
    }

    //User Constructor
    public User initUser (String username, String password){
        User user = new User();
        String encryptedPassword = "{bcrypt}"+passwordEncoder.encode(password);
        user.setUsername(username);
        user.setPassword(encryptedPassword);
        user.setEnabled(1);
        return user;
    }

    public Authority initAuthority (String username){
        Authority authorities = new Authority();
        authorities.setUsername(username);
        authorities.setAuthority("ROLE_USER");
        return authorities;
    }
}
