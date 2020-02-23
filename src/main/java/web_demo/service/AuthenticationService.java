package web_demo.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    public boolean isSessionValid(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !authentication.getName().equals("anonymousUser");
    }

    public Map<String, String> getSessionData(){
        String principalUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();

        Map<String, String> sessionData = new HashMap<>();
        sessionData.put("username", principalUsername);
        sessionData.put("sessionId", sessionId);
        return sessionData;
    }
}
