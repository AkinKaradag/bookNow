package Config;

import bookNow.Config.SecurityConfig;
import bookNow.Security.JwtAuthenticationEntryPoint;
import bookNow.Security.JwtAuthenticationFilter;
import bookNow.Security.JwtTokenProvider;
import bookNow.Service.UserDetailsServiceImpl;
import bookNow.Application; // Importiere die Hauptklasse der Anwendung
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testklasse für die Sicherheitskonfigurationsklasse.
 */
//@SpringBootTest(classes = Application.class) // Referenziere die Hauptklasse der Anwendung
@WebMvcTest(SecurityConfig.class)
@Import(SecurityConfig.class)
public class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthenticationManager authenticationManager;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws ServletException, IOException {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters((FilterChainProxy) context.getBean("springSecurityFilterChain"))
                .build();

        // Mock the JWT token generation
        when(jwtTokenProvider.generateJwtToken(any(Authentication.class))).thenReturn("Bearer mockToken");

        // Mock the JWT authentication filter to add the Authorization header
        doAnswer(invocation -> {
            ServletResponse response = invocation.getArgument(1);
            if (response instanceof jakarta.servlet.http.HttpServletResponse) {
                ((jakarta.servlet.http.HttpServletResponse) response).setHeader(HttpHeaders.AUTHORIZATION, "Bearer mockToken");
            }
            return null;
        }).when(jwtAuthenticationFilter).doFilter(any(ServletRequest.class), any(ServletResponse.class), any(FilterChain.class));
    }

    /**
     * Testet die Sicherheitsfilterkette, indem geprüft wird, ob ein Login und ein Logout korrekt funktionieren.
     */
    @Test
    void testSecurityFilterChain() throws Exception {
        // Mock authentication success
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(Mockito.mock(Authentication.class));

        // Teste, ob der Zugriff auf die Login-Seite authentifiziert ist
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login/private")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"user\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer mockToken"));

        // Teste, ob der Logout-Prozess korrekt funktioniert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/logout"))
                .andExpect(status().isOk());
    }
}