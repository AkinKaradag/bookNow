package bookNow.Security;
import io.jsonwebtoken.Jwts;
import bookNow.Model.UserType;
import bookNow.Service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Diese Klasse ist ein Filter, der auf jeder Anfrage ausgeführt wird, um das JWT-Token zu überprüfen.
 * Wenn das Token gültig ist, wird der Benutzer authentifiziert und im SecurityContext gespeichert.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    public JwtAuthenticationFilter() {
    }


    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    /**
     * Überschreibt die Methode, um bestimmte URLs wie "/auth/register" und "/auth/login" von der Filterung auszuschliessen.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/auth/register") || path.startsWith("/auth/login");
    }

    /**
     * Überprüft das JWT-Token in der Anfrage und authentifiziert den Benutzer, falls das Token gültig ist.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwtToken = extractJwtFromRequest(request);

            if (StringUtils.hasText(jwtToken)) {
                // Parsen des JWT-Tokens und Claims auslesen
                Claims claims = Jwts.parser()
                        .verifyWith(jwtTokenProvider.getKey())
                        .build()
                        .parseSignedClaims(jwtToken)
                        .getPayload();

                Integer idInt = (Integer) claims.get("id", Integer.class);
                Long id = idInt.longValue();
                UserType userType = UserType.valueOf((String) claims.get("userType"));

                UserDetails userDetails;
                if (userType == UserType.COMPANYUSER) {
                    userDetails = userDetailsService.loadCompanyById(id);
                } else {
                userDetails = userDetailsService.loadUserById(id);

                }

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch (Exception e) {
                        logger.error("Could not set user authentication in security context", e);
                        return;
                    }
        filterChain.doFilter(request, response);
    }

    /**
     * Extrahiert das JWT-Token aus dem Authorization-Header der Anfrage.
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))
            return bearer.substring("Bearer".length() + 1);
        return null;

    }
}
