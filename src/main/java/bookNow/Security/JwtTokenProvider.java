package bookNow.Security;

import bookNow.Model.UserType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

/**
 * Diese Klasse verwaltet die Erstellung und Validierung von JWT-Tokens.
 * Das Token enthält Benutzer-ID und Benutzer-Typ, die für die Authentifizierung und Autorisierung verwendet werden.
 */
@Component
public class JwtTokenProvider {

    @Value("${bookNow.app.secret}")
    private String APP_SECRET; // Geheimschlüssel für die Signatur des Tokens
    @Value("${bookNow.app.expires.in}")
    private long EXPIRES_IN; // Token-Ablaufzeit in Sekunden

    /**
     * Liefert den geheimen Schlüssel für die Token-Generierung und Validierung.
     */
    public SecretKey getKey() {
        return Keys.hmacShaKeyFor(APP_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Erstellt ein JWT-Token für die angegebene Authentifizierung.
     */

    // Konstruktor für Testzwecke
    public JwtTokenProvider(String appSecret, long expiresIn) {
        this.APP_SECRET = appSecret;
        this.EXPIRES_IN = expiresIn;
    }

    public JwtTokenProvider() {
    }


    public String generateJwtToken(Authentication auth) {

        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN * 1000);

        if (userDetails.getUserType() == null) {
            throw new IllegalArgumentException("User type not set");
        }
        return Jwts.builder()
                    .claim("id", (userDetails.getId()))
                    .claim("userType", userDetails.getUserType().toString())
                    .claim("issuedAt", new Date())
                    .claim("exp", expireDate)
                    .signWith(getKey())
                    .compact();
    }

    /**
     * Erstellt ein JWT-Token basierend auf der Benutzer-ID und dem Benutzer-Typ.
     */
    public String generateJwtTokenById(Long id, UserType userType) {
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN * 1000);
        return Jwts.builder()
                .claim("id", id)
                .claim("userType", userType.toString())
                .claim("issuedAt", new Date())
                .claim("exp", expireDate)
                .signWith(getKey())
                .compact();
    }

    protected Long getUserIdFromJwt(String token) {

        //Claims claim = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
        Jws<Claims> claim = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
        Claims claims = claim.getPayload();
        Object idObject = claims.get("id");


        if (idObject == null) {
            throw new IllegalArgumentException("User id not found in token");
        }
        if (idObject instanceof Integer) {
            return ((Integer) idObject).longValue();
        } else if (idObject instanceof Long) {
            return (Long) idObject;
        } else {

            return Long.parseLong(idObject.toString());

    }
    }

    public UserType getUserTypeFromJwt(String token) {
        try {
            Claims claim = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
            UserType userType = UserType.valueOf(claim.get("userType", String.class));
            return userType;
        } catch (Exception e) {
            System.out.println("Error extracting UserType from Token: " + e.getMessage());
            return null;
        }
    }


    /**
     * Überprüft, ob das angegebene Token gültig ist.
     */

        protected boolean validateToken(String token) {
            System.out.println("Validating Token: " + token);
            try {
                Jws<Claims> claims = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
                System.out.println("Token validated. Claims: " + claims);
                return !isTokenExpired(token);
            } catch (JwtException e) {
                System.out.println("Token validation failed: " + e.getMessage());
                return false;
            }
        }

        /**
         * Überprüft, ob das angegebene Token abgelaufen ist.
         */
    private boolean isTokenExpired(String token) {
        try {
        Date expiration = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getExpiration();
        return expiration.before(new Date());
    } catch(Exception e) {
        System.out.println("Token expired");
        return true;
    }
    }

}
