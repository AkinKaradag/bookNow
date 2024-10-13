package bookNow.Controller;

import bookNow.Model.CompanyModel;
import bookNow.Model.RefreshTokenModel;
import bookNow.Model.UserModel;
import bookNow.Model.UserType;
import bookNow.Requests.CompanyRequest;
import bookNow.Requests.RefreshTokenRequest;
import bookNow.Requests.UserRequest;
import bookNow.Response.AuthResponse;
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
    public AuthResponse login(@RequestBody UserRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        UserModel user = userService.findByName(loginRequest.getName());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken("Bearer " + jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user, null));
        authResponse.setId(user.getId());
        authResponse.setUserType(user.getUserType());
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
        authResponse.setAccessToken("Bearer " + jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(null, company));
        authResponse.setId(company.getid());
        authResponse.setUserType(company.getUserType());
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

       UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(registerRequest.getName(), registerRequest.getPassword());
       Authentication auth = authenticationManager.authenticate(authToken);
       SecurityContextHolder.getContext().setAuthentication(auth);
       String jwtToken = jwtTokenProvider.generateJwtToken(auth);

       authResponse.setMessage("Registration successful");
       authResponse.setAccessToken("Bearer " + jwtToken);
       authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user, null));
       authResponse.setId(user.getId());
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
            company.setPhoneNumber(registerRequest.getPhoneNumber());
            company.setDescription(registerRequest.getDescription());
            company.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            company.setUserType(registerRequest.getUserType());
            companyService.createCompany(company);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(registerRequest.getCompanyName(), registerRequest.getPassword());
            Authentication auth = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwtToken = jwtTokenProvider.generateJwtToken(auth);

            authResponse.setMessage("Registration successful");
            authResponse.setAccessToken("Bearer " + jwtToken);
            authResponse.setRefreshToken(refreshTokenService.createRefreshToken(null, company));
            authResponse.setId(company.getid());
            return new ResponseEntity<>(authResponse, HttpStatus.OK);


        }

    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthResponse response = new AuthResponse();
        RefreshTokenModel token = refreshTokenService.getByUserOrCompany(refreshTokenRequest.getId(), refreshTokenRequest.isCompanyUser());
        if(token.getToken().equals(refreshTokenRequest.getRefreshToken()) &&
                !refreshTokenService.isRefreshExpired(token)) {

            Long id = refreshTokenRequest.isCompanyUser() ? token.getCompany().getid() : token.getUser().getId();
            UserType userType = refreshTokenRequest.isCompanyUser() ? UserType.COMPANYUSER : UserType.PRIVATEUSER;
            String jwtToken = jwtTokenProvider.generateJwtTokenById(id, userType);
            response.setMessage("token successfully refreshed.");
            response.setAccessToken("Bearer " + jwtToken);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

    }





}