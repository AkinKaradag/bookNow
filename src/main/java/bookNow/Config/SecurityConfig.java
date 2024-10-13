package bookNow.Config;

import bookNow.Security.JwtAuthenticationEntryPoint;
import bookNow.Security.JwtAuthenticationFilter;
import bookNow.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Diese Konfigurationsklasse definiert die Sicherheitseinstellungen für die Anwendung.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private UserDetailsServiceImpl userDetailsService;

    private JwtAuthenticationEntryPoint handler;

    // Konstruktor-Injektion für die Sicherheitskomponenten
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthenticationEntryPoint handler) {
        this.userDetailsService = userDetailsService;
        this.handler = handler;
    }

    /**
     * Bean für den JWT-Authentifizierungsfilter, um Anfragen mit JWT zu verarbeiten.
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * Bean für den Passwort-Encoder. Verwendet BCrypt, um Passwörter zu hashen.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean für den Authentication Manager, der die Authentifizierung der Benutzer verwaltet.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Konfiguration der CORS-Einstellungen, um Cross-Origin-Requests zu ermöglichen.
     */
    @Bean
    public CorsConfigurationSource corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Konfiguration der Sicherheitsfilterkette. Hier werden Zugriffsrechte für Endpunkte definiert,
     * und der JWT-Authentifizierungsfilter wird in die Kette eingebunden.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsFilter()))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling( exceptions -> exceptions.authenticationEntryPoint(handler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/appointments").hasAnyRole("COMPANYUSER", "PRIVATEUSER")
                .requestMatchers(HttpMethod.GET, "/appointments").hasAnyRole("COMPANYUSER", "PRIVATEUSER")
                .requestMatchers(HttpMethod.GET, "/appointments/*").hasAnyRole("COMPANYUSER", "PRIVATEUSER")
                .requestMatchers(HttpMethod.GET, "/appointments/**").hasAnyRole("COMPANYUSER", "PRIVATEUSER")
                .requestMatchers(HttpMethod.PUT, "/appointments/*").hasAnyRole("COMPANYUSER", "PRIVATEUSER")
                .requestMatchers(HttpMethod.DELETE, "/appointments/*").hasAnyRole("COMPANYUSER", "PRIVATEUSER")
                .requestMatchers(HttpMethod.POST, "/service-companies/**").hasRole("COMPANYUSER")
                .requestMatchers(HttpMethod.PUT, "/service-companies/*").hasRole("COMPANYUSER")
                .requestMatchers(HttpMethod.DELETE, "/service-companies/*").hasRole("COMPANYUSER")
                .requestMatchers(HttpMethod.GET, "/users/*").hasRole("PRIVATEUSER")
                .requestMatchers(HttpMethod.GET, "/users/**").hasRole("PRIVATEUSER")
                .requestMatchers(HttpMethod.PUT, "/users/*").hasRole("PRIVATEUSER")
                .requestMatchers(HttpMethod.POST, "/users/*").hasRole("PRIVATEUSER")
                .requestMatchers(HttpMethod.GET, "/companies/*").hasRole("COMPANYUSER")
                .requestMatchers(HttpMethod.GET, "/companies/**").hasRole("COMPANYUSER")
                .requestMatchers(HttpMethod.PUT, "/companies/*").hasRole("COMPANYUSER")
                .requestMatchers(HttpMethod.POST, "/companies/*").hasRole("COMPANYUSER")
                .requestMatchers(HttpMethod.GET, "/service-companies").permitAll()
                .requestMatchers(HttpMethod.GET, "/service-companies/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .anyRequest().authenticated()
                )
                //.formLogin(AbstractHttpConfigurer::disable);

                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }


}
