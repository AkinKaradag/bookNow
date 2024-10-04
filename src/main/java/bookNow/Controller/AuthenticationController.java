package bookNow.Controller;

import bookNow.Model.CompanyModel;
import bookNow.Model.UserModel;
import bookNow.Requests.CompanyRequest;
import bookNow.Requests.UserRequest;
import bookNow.Response.AuthResponse;
import bookNow.Security.JwtTokenProvider;
import bookNow.Service.CompanyService;
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

    private CompanyService companyService;

    private PasswordEncoder passwordEncoder;

    private static final String EMAIL_REGEX = "^(.+)@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder, CompanyService companyService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login/private")
    public AuthResponse login(@RequestBody UserRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        UserModel user = userService.findByName(loginRequest.getName());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Bearer " + jwtToken);
        authResponse.setId(user.getId());
        return authResponse;
    }

    @PostMapping("/login/company")
    public AuthResponse login(@RequestBody CompanyRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getCompanyName(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        CompanyModel company = companyService.findByName(loginRequest.getCompanyName());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Bearer " + jwtToken);
        authResponse.setId(company.getCompanyId());
        return authResponse;
    }

    @PostMapping("/register/private")
    public ResponseEntity<AuthResponse> registerPrivate (@RequestBody UserRequest registerRequest){
        AuthResponse authResponse = new AuthResponse();
       if (userService.findByName(registerRequest.getName()) != null) {
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
       user.setName(registerRequest.getName());
       user.setEmail(registerRequest.getEmail());
       user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
       user.setUserType(registerRequest.getUserType());
       userService.createUser(user);
       authResponse.setMessage("Registration successful");
       return new ResponseEntity<>(authResponse, HttpStatus.OK);


            }

    }

    @PostMapping("/register/company")
    public ResponseEntity<AuthResponse> registerCompany (@RequestBody CompanyRequest registerRequest){
        AuthResponse authResponse = new AuthResponse();
        if (companyService.findByName(registerRequest.getCompanyName()) != null) {
            authResponse.setMessage("Company already exists");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        } else {
            CompanyModel company = new CompanyModel();
            company.setCompanyName(registerRequest.getCompanyName());
            company.setCompanyAddress(registerRequest.getCompanyAddress());
            company.setCompanyCity(registerRequest.getCompanyCity());
            company.setCompanyPostalCode(registerRequest.getCompanyPostalCode());

            company.setUserType(registerRequest.getUserType());
            companyService.createCompany(company);
            authResponse.setMessage("Registration successful");
            return new ResponseEntity<>(authResponse, HttpStatus.OK);


        }

    }

}