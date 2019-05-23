package bankapp;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.AuthenticationService;
import service.UserService;
import utils.Constants;
import utils.SessionUtil;
import javax.servlet.ServletException;
import java.util.Date;

public class Login extends OAuth2ResourceServerProperties.Jwt {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User user1) throws ServletException {

        String token = "";

        if (user1.getUsername() == null || user1.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }

        String username = user1.getUsername();
        String password = user1.getPassword();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + Constants.EXPIRATION_IN_MS);

        User user = userService.findByUserName(username);

        if (user == null) {
            throw new ServletException("User not found.");
        }

        String pwd = user.getPassword();

        if (!password.equals(pwd)) {
            throw new ServletException("Invalid login. Please check your name and password.");
        }
        SessionUtil.user = user;


        token = Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(expiryDate).signWith(SignatureAlgorithm.HS256,authenticationService.login(username,password))
                .compact();
        return token;
    }
}
