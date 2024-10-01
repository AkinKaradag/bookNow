package bookNow.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${bookNow.app.secret}")
    private String APP_SECRET;
    @Value("${bookNow.app.expires.in}")
    private long EXPIRES_IN;

    SecretKey key = Keys.hmacShaKeyFor(APP_SECRET.getBytes());

    public String generateJwtToken(Authentication auth) {

        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);

        return Jwts.builder()
                .claim("id", Long.toString(userDetails.getId()))
                .claim("issuedAt", new Date())
                .claim("expiration", expireDate)
                .signWith(key)
                .compact();
    }


    Long getUserIdFromJwt(String token) {

        Claims claim = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return Long.parseLong(claim.getSubject());
    }

    boolean validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(APP_SECRET.getBytes());
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return !isTokenExpired(token);
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getExpiration();
        return expiration.before(new Date());
    }

}
