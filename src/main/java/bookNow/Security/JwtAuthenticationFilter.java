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

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("Request received at JwtAuthenticationFilter");
        try {
            String jwtToken = extractJwtFromRequest(request);
            System.out.println("JWT Token: " + jwtToken);

            if (StringUtils.hasText(jwtToken)) {
                // Parsen des JWT-Tokens und Claims auslesen
                Claims claims = Jwts.parser()
                        .verifyWith(jwtTokenProvider.getKey())
                        .build()
                        .parseSignedClaims(jwtToken)
                        .getPayload();

                Integer idInt = (Integer) claims.get("id", Integer.class);
                Long id = idInt.longValue();
                System.out.println("User ID: " + id);

                UserType userType = UserType.valueOf((String) claims.get("userType"));
                System.out.println("User Type: " + userType);

                UserDetails userDetails;
                if (userType == UserType.COMPANYUSER) {
                    userDetails = userDetailsService.loadCompanyById(id);
                } else {
                userDetails = userDetailsService.loadUserById(id);

                }

                if (userDetails != null) {
                    System.out.println("Benutzer gefunden: " + userType);
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


    private String extractJwtFromRequest(HttpServletRequest request) {
        System.out.println("Authorization Header: " + request.getHeader("Authorization"));

        String bearer = request.getHeader("Authorization");
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))
            return bearer.substring("Bearer".length() + 1);
        return null;

    }
}
