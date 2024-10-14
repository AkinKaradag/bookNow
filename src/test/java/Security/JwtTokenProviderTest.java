package Security;

import bookNow.Model.UserType;
import bookNow.Security.JwtTokenProvider;
import bookNow.Security.JwtUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtTokenProviderTest {

    private JwtTokenProviderUnderTest jwtTokenProvider;
    private static final String APP_SECRET = "supersecretkeythatissupposedtobeverysecure";
    private static final long EXPIRES_IN = 3600L;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        jwtTokenProvider = new JwtTokenProviderUnderTest(APP_SECRET, EXPIRES_IN);
    }

    @Test
    public void testGenerateJwtToken() {
        JwtUserDetails userDetails = new JwtUserDetails(1L, "testuser", "password", UserType.PRIVATEUSER, Collections.singletonList(new SimpleGrantedAuthority("ROLE_PRIVATEUSER")));
        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        String token = jwtTokenProvider.generateJwtToken(authentication);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ")); // JWTs typically start with "eyJ"
    }

    @Test
    public void testGetUserIdFromJwt() {
        JwtUserDetails userDetails = new JwtUserDetails(1L, "testuser", "password", UserType.PRIVATEUSER, Collections.singletonList(new SimpleGrantedAuthority("ROLE_PRIVATEUSER")));
        String token = jwtTokenProvider.generateJwtTokenById(userDetails.getId(), userDetails.getUserType());

        Long userId = jwtTokenProvider.getUserIdFromJwt(token);
        assertEquals(userDetails.getId(), userId);
    }

    @Test
    public void testValidateToken_validToken() {
        JwtUserDetails userDetails = new JwtUserDetails(1L, "testuser", "password", UserType.PRIVATEUSER, Collections.singletonList(new SimpleGrantedAuthority("ROLE_PRIVATEUSER")));
        String token = jwtTokenProvider.generateJwtTokenById(userDetails.getId(), userDetails.getUserType());

        boolean isValid = jwtTokenProvider.validateToken(token);
        assertTrue(isValid);
    }

    @Test
    public void testValidateToken_invalidToken() {
        String invalidToken = "invalid.token.string";
        boolean isValid = jwtTokenProvider.validateToken(invalidToken);
        assertFalse(isValid);
    }

    // Unterklasse, um Zugriff auf geschützte Methoden zu ermöglichen
    private static class JwtTokenProviderUnderTest extends JwtTokenProvider {
        public JwtTokenProviderUnderTest(String appSecret, long expiresIn) {
            super(appSecret, expiresIn);
        }

        @Override
        public SecretKey getKey() {
            return Keys.hmacShaKeyFor(APP_SECRET.getBytes(StandardCharsets.UTF_8));
        }

        @Override
        protected Long getUserIdFromJwt(String token) {
            return super.getUserIdFromJwt(token);
        }

        @Override
        protected boolean validateToken(String token) {
            return super.validateToken(token);
        }
    }
}