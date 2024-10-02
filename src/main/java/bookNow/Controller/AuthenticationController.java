package bookNow.Controller;

import bookNow.Model.UserModel;
import bookNow.Requests.UserRequest;
import bookNow.Response.AuthResponse;
import bookNow.Security.JwtTokenProvider;
import bookNow.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    private static final String EMAIL_REGEX = "^(.+)@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        UserModel user = userService.findByName(loginRequest.getUsername());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Bearer " + jwtToken);
        authResponse.setUserId(user.getId());
        return authResponse;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register (@RequestBody UserRequest registerRequest){
        AuthResponse authResponse = new AuthResponse();
       if (userService.findByName(registerRequest.getUsername()) != null) {
           authResponse.setMessage("Username already exists");
             return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
         } else if(userService.findByEmail(registerRequest.getEmail()) != null) {
           authResponse.setMessage("Email already exists");
           return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
       } else if(!EMAIL_PATTERN.matcher(registerRequest.getEmail()).matches()) {
           authResponse.setMessage("Invalid email");
           return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
       } else {
       UserModel user = new UserModel();
       user.setName(registerRequest.getUsername());
       user.setEmail(registerRequest.getEmail());
       user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
       userService.createUser(user);
       authResponse.setMessage("Registration successful");
       return new ResponseEntity<>(authResponse, HttpStatus.OK);


            }

    }

}