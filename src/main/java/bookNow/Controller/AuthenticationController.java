package bookNow.Controller;

import bookNow.Model.CompanyModel;
import bookNow.Model.RefreshTokenModel;
import bookNow.Model.UserModel;
import bookNow.Model.UserType;
import bookNow.Requests.CompanyRequest;
import bookNow.Requests.RefreshTokenRequest;
import bookNow.Requests.UserRequest;
import bookNow.Response.AuthResponse;
import bookNow.Response.LoginResponse;
import bookNow.Response.RegistrationResponse;
import bookNow.Security.JwtTokenProvider;
import bookNow.Service.CompanyService;
import bookNow.Service.RefreshTokenService;
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

import java.util.Date;
import java.util.regex.Pattern;

/**
 * Controller für die Authentifizierungsoperationen, inklusive Login, Registrierung und Token-Refresh.
 */

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    // Service- und Authentifizierungs-Abhängigkeiten
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private CompanyService companyService;
    private PasswordEncoder passwordEncoder;
    private RefreshTokenService refreshTokenService;


    private static final String EMAIL_REGEX = "^(.+)@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Methoden für Login, Registrierung und Token-Refresh
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder, CompanyService companyService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login/private")
    public LoginResponse login(@RequestBody UserRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        UserModel user = userService.findByName(loginRequest.getName());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken("Bearer " + jwtToken);
        loginResponse.setRefreshToken(refreshTokenService.createRefreshToken(user, null));
        loginResponse.setId(user.getId());
        loginResponse.setUserType(user.getUserType());
        return loginResponse;
    }


    @PostMapping("/login/company")
    public LoginResponse login(@RequestBody CompanyRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getCompanyName(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        CompanyModel company = companyService.findByName(loginRequest.getCompanyName());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken("Bearer " + jwtToken);
        loginResponse.setRefreshToken(refreshTokenService.createRefreshToken(null, company));
        loginResponse.setId(company.getId());
        loginResponse.setUserType(company.getUserType());
        return loginResponse;
    }

    @PostMapping("/register/private")
    public ResponseEntity<RegistrationResponse> registerPrivate (@RequestBody UserRequest registerRequest){

        RegistrationResponse registrationResponse = new RegistrationResponse();
       if (userService.findByName(registerRequest.getName()) != null) {
           registrationResponse.setMessage("Username already exists");
             return new ResponseEntity<>(registrationResponse, HttpStatus.BAD_REQUEST);
         } else if(userService.findByEmail(registerRequest.getEmail()) != null) {
           registrationResponse.setMessage("Email already exists");
           return new ResponseEntity<>(registrationResponse, HttpStatus.BAD_REQUEST);
       } else if(!EMAIL_PATTERN.matcher(registerRequest.getEmail()).matches()) {
           registrationResponse.setMessage("Invalid email");
           return new ResponseEntity<>(registrationResponse, HttpStatus.BAD_REQUEST);
       } else {
       UserModel user = new UserModel();
       user.setName(registerRequest.getName());
       user.setEmail(registerRequest.getEmail());
       user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
       user.setUserType(registerRequest.getUserType());
       userService.createUser(user);
       registrationResponse.setMessage("Registration successful");
       registrationResponse.setId(user.getId());
       registrationResponse.setUserType(user.getUserType());
       return new ResponseEntity<>(registrationResponse, HttpStatus.OK);
       }

    }

    @PostMapping("/register/company")
    public ResponseEntity<RegistrationResponse> registerCompany (@RequestBody CompanyRequest registerRequest){
        RegistrationResponse registrationResponse = new RegistrationResponse();
        if (companyService.findByName(registerRequest.getCompanyName()) != null) {
            registrationResponse.setMessage("Company already exists");
            return new ResponseEntity<>(registrationResponse, HttpStatus.BAD_REQUEST);
        } else {
            CompanyModel company = new CompanyModel();
            company.setCompanyName(registerRequest.getCompanyName());
            company.setCompanyAddress(registerRequest.getCompanyAddress());
            company.setCompanyCity(registerRequest.getCompanyCity());
            company.setCompanyPostalCode(registerRequest.getCompanyPostalCode());
            company.setPhoneNumber(registerRequest.getPhoneNumber());
            company.setDescription(registerRequest.getDescription());
            company.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            company.setUserType(registerRequest.getUserType());
            companyService.createCompany(company);
            registrationResponse.setMessage("Registration successful");
            registrationResponse.setId(company.getId());
            registrationResponse.setUserType(company.getUserType());
            return new ResponseEntity<>(registrationResponse, HttpStatus.OK);
        }

    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthResponse response = new AuthResponse();
        RefreshTokenModel token = refreshTokenService.getByUserOrCompany(refreshTokenRequest.getId(), refreshTokenRequest.isCompanyUser());


        if(token.getToken().trim().equals(refreshTokenRequest.getRefreshToken().trim()) &&
                !refreshTokenService.isRefreshExpired(token)) {
            Long id = refreshTokenRequest.isCompanyUser() ? token.getCompany().getId() : token.getUser().getId();
            UserType userType = refreshTokenRequest.isCompanyUser() ? UserType.COMPANYUSER : UserType.PRIVATEUSER;
            String jwtToken = jwtTokenProvider.generateJwtTokenById(id, userType);
            response.setId(id);
            response.setUserType(userType);
            response.setRefreshToken(token.getToken());
            response.setMessage("token successfully refreshed.");
            response.setAccessToken("Bearer " + jwtToken);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

    }
}