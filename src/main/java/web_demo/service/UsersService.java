package web_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import web_demo.entity.Authorities;
import web_demo.entity.Users;
import web_demo.repository.UsersRepository;

@Service
public class UsersService {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //Sign-up Format
    String passwordFormat = "^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9])))(?=.{6,})";
    String usernameFormat = "(?=.*?[0-9a-zA-Z]).{6,20}";

    public boolean isPasswordValid (String password){
        return password.matches(passwordFormat);
    }

    public boolean isUsernameValid (String username){
        return username.matches(usernameFormat);
    }

    //User Constructor
    public Users initUser (String username, String password){
        Users users = new Users();
        String encryptedPassword = "{bcrypt}"+passwordEncoder.encode(password);
        users.setUsername(username);
        users.setPassword(encryptedPassword);
        users.setEnabled(1);
        return users;
    }

    public Authorities initAuthority (String username){
        Authorities authorities = new Authorities();
        authorities.setUsername(username);
        authorities.setAuthority("ROLE_USER");
        return authorities;
    }
}
