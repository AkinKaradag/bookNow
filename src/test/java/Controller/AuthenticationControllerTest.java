package Controller;

import bookNow.Controller.AuthenticationController;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserService userService;

    @Mock
    private CompanyService companyService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testet den erfolgreichen Login eines privaten Benutzers.
     */
    @Test
    void testLoginPrivateUser_Success() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("testUser");
        userRequest.setPassword("password");

        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setUserType(UserType.PRIVATEUSER);

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.generateJwtToken(authentication)).thenReturn("jwtToken");
        when(userService.findByName("testUser")).thenReturn(userModel);
        when(refreshTokenService.createRefreshToken(userModel, null)).thenReturn("refreshToken");

        LoginResponse response = authenticationController.login(userRequest);

        assertNotNull(response);
        assertEquals("Bearer jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        assertEquals(userModel.getId(), response.getId());
        assertEquals(userModel.getUserType(), response.getUserType());
    }

    /**
     * Testet den erfolgreichen Login eines Firmenbenutzers.
     */
    @Test
    void testLoginCompanyUser_Success() {
        CompanyRequest companyRequest = new CompanyRequest();
        companyRequest.setCompanyName("testCompany");
        companyRequest.setPassword("password");

        CompanyModel companyModel = new CompanyModel();
        companyModel.setId(1L);
        companyModel.setUserType(UserType.COMPANYUSER);

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.generateJwtToken(authentication)).thenReturn("jwtToken");
        when(companyService.findByName("testCompany")).thenReturn(companyModel);
        when(refreshTokenService.createRefreshToken(null, companyModel)).thenReturn("refreshToken");

        LoginResponse response = authenticationController.login(companyRequest);

        assertNotNull(response);
        assertEquals("Bearer jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        assertEquals(companyModel.getid(), response.getId());
        assertEquals(companyModel.getUserType(), response.getUserType());
    }

    /**
     * Testet die Registrierung eines privaten Benutzers, wenn der Benutzername bereits existiert.
     */
    @Test
    void testRegisterPrivateUser_UsernameExists() {
        UserRequest registerRequest = new UserRequest();
        registerRequest.setName("testUser");

        when(userService.findByName("testUser")).thenReturn(new UserModel());

        ResponseEntity<RegistrationResponse> response = authenticationController.registerPrivate(registerRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username already exists", response.getBody().getMessage());
    }

    /**
     * Testet die erfolgreiche Registrierung eines Firmenbenutzers.
     */
    @Test
    void testRegisterCompany_Success() {
        CompanyRequest registerRequest = new CompanyRequest();
        registerRequest.setCompanyName("testCompany");
        registerRequest.setPassword("password");

        when(companyService.findByName("testCompany")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        ResponseEntity<RegistrationResponse> response = authenticationController.registerCompany(registerRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registration successful", response.getBody().getMessage());
    }

    /**
     * Testet das erfolgreiche Aktualisieren eines Tokens mit einem gültigen Refresh-Token.
     */
    @Test
    void testRefreshToken_Success() {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setId(1L);
        refreshTokenRequest.setRefreshToken("validRefreshToken");
        refreshTokenRequest.setCompanyUser(true);

        RefreshTokenModel tokenModel = new RefreshTokenModel();
        CompanyModel company = new CompanyModel();
        company.setId(1L);
        tokenModel.setCompany(company);
        tokenModel.setToken("validRefreshToken");

        when(refreshTokenService.getByUserOrCompany(1L, true)).thenReturn(tokenModel);
        when(refreshTokenService.isRefreshExpired(tokenModel)).thenReturn(false);
        when(jwtTokenProvider.generateJwtTokenById(1L, UserType.COMPANYUSER)).thenReturn("newJwtToken");

        ResponseEntity<AuthResponse> response = authenticationController.refresh(refreshTokenRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token successfully refreshed.", response.getBody().getMessage());
        assertEquals("Bearer newJwtToken", response.getBody().getAccessToken());
    }
}

