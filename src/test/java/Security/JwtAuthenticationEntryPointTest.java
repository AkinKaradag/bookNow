package Security;

import bookNow.Security.JwtAuthenticationEntryPoint;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * Testet die JwtAuthenticationEntryPoint-Klasse, die nicht authentifizierte Anfragen abfängt.
 */
public class JwtAuthenticationEntryPointTest {

    private JwtAuthenticationEntryPoint entryPoint;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entryPoint = new JwtAuthenticationEntryPoint();
    }

    @Test
    void testCommence_sendsUnauthorizedError() throws IOException, ServletException {


        // Act: Führe die commence-Methode aus
        entryPoint.commence(request, response, authException);

        // Assert: Überprüfe, dass der 401-Fehler gesendet wurde
        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}

