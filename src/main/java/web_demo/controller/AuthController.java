package web_demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @ResponseStatus(reason = "Login Success", value = HttpStatus.OK)
    @RequestMapping("/loginSuccess")
    public String successController (){
        return "Success";
    }


    @RequestMapping("/loginFailed")
    public Map<String, String> failureController (HttpServletRequest request, HttpServletResponse response){
        Map<String, String> message = new HashMap<>();
        message.put("status", "403");
        message.put("message", "Login Failed");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        return message;
        //or ==> return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
