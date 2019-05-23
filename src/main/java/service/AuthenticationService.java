package service;

import model.Authentication;
import model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AuthenticationRepository;
import utils.Constants;
import utils.RandomString;
import java.util.Optional;

@Service("authenticationService")
public class AuthenticationService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    public String login(String username, String password) {
        Optional authentication = authenticationRepository.login(username,password);
        if(authentication.isPresent()){
            String token = null;
            while(!validateToken(token)) {
                token = RandomString.getAlphaNumericString(Constants.TOKEN_LENGTH);
            }
            Authentication auth = (Authentication) authentication.get();
            auth.setToken(token);
            return token;
        }
        return StringUtils.EMPTY;
    }

    public Optional findByToken(String token) {
        Optional authentication= authenticationRepository.findByToken(token);
        if(authentication.isPresent()){
            Authentication authentication1 = (Authentication) authentication.get();
            User user= new User(authentication1);
            return Optional.of(user);
        }
        return  Optional.empty();
    }

    public boolean validateToken(String token) {

        if (authenticationRepository.findByToken(token).isPresent()) {
            return false;
        } else {
            return true;
        }
    }

}

