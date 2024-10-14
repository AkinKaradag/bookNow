package Security;

import bookNow.Model.UserType;
import bookNow.Security.JwtAuthenticationFilter;
import bookNow.Security.JwtTokenProvider;
import bookNow.Service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testklasse für JwtAuthenticationFilter und JwtTokenProvider.
 * Diese Klasse umfasst Tests für Methoden mit privatem und geschütztem Zugriff.
 */
public class JwtAuthenticationFilterTest {

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private HttpServletRequest request;

    private JwtTokenProviderUnderTest jwtTokenProviderUnderTest;
    private JwtAuthenticationFilterUnderTest jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        // Initialisiert die Mocks für die Tests
        MockitoAnnotations.openMocks(this);

        // Initialisiere die Testvarianten von JwtTokenProvider und JwtAuthenticationFilter
        jwtTokenProviderUnderTest = new JwtTokenProviderUnderTest("my-test-secret-key", 3600L);
        jwtAuthenticationFilter = new JwtAuthenticationFilterUnderTest(jwtTokenProviderUnderTest, userDetailsService);
    }

    /**
     * Eine Unterklasse von JwtAuthenticationFilter, die die Sichtbarkeit geschützter Methoden erhöht.
     * Die Unterklasse wird nur für Testzwecke verwendet.
     */
    private class JwtAuthenticationFilterUnderTest extends JwtAuthenticationFilter {
        public JwtAuthenticationFilterUnderTest(JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
            super(jwtTokenProvider, userDetailsService);
        }

        @Override
        public boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
            return super.shouldNotFilter(request);
        }

        @Override
        public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            super.doFilterInternal(request, response, filterChain);
        }
    }

    /**
     * Eine Unterklasse von JwtTokenProvider, die es ermöglicht, Konstruktorparameter für Testzwecke zu setzen.
     */
    private class JwtTokenProviderUnderTest extends JwtTokenProvider {
        public JwtTokenProviderUnderTest(String appSecret, long expiresIn) {
            super(appSecret, expiresIn);
        }

        @Override
        public boolean validateToken(String token) {
            return super.validateToken(token);
        }

        @Override
        public Long getUserIdFromJwt(String token) {
            return super.getUserIdFromJwt(token);
        }
    }

    /**
     * Testet, ob die `shouldNotFilter`-Methode für die Registrierungs- und Login-URLs true zurückgibt.
     */
    @Test
    void testShouldNotFilter_withAuthPath() throws ServletException {
        // Arrange: Mock des Request-Objekts mit einer URL, die die Filterung überspringen sollte
        when(request.getRequestURI()).thenReturn("/auth/login");

        // Act & Assert: Sicherstellen, dass `shouldNotFilter` true zurückgibt
        assertTrue(jwtAuthenticationFilter.shouldNotFilter(request));
    }

    /**
     * Testet die `shouldNotFilter`-Methode für eine nicht autorisierte URL.
     */
    @Test
    void testShouldNotFilter_withNonAuthPath() throws ServletException {
        // Arrange: Mock des Request-Objekts mit einer anderen URL
        when(request.getRequestURI()).thenReturn("/non/protected/path");

        // Act & Assert: Sicherstellen, dass `shouldNotFilter` false zurückgibt
        assertFalse(jwtAuthenticationFilter.shouldNotFilter(request));
    }
}
