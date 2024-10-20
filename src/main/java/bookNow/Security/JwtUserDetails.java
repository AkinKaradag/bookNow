package bookNow.Security;

import bookNow.Model.CompanyModel;
import bookNow.Model.UserModel;
import bookNow.Model.UserType;
import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Diese Klasse implementiert die UserDetails-Schnittstelle von Spring Security und stellt Details
 * für die Authentifizierung und Autorisierung des Benutzers bereit.
 */
@Getter
@Setter
public class JwtUserDetails implements UserDetails {


    private Long id; // Benutzer-ID
    private String username; // Benutzername oder Firmenname
    private String password; // Benutzerpasswort

    private UserType userType; // Benutzer-Typ (z.B. PRIVATEUSER oder COMPANYUSER)
    private Collection<? extends GrantedAuthority> authorities; // Berechtigungen des Benutzers

    // Konstruktor für das Initialisieren der Benutzerinformationen und Berechtigungen
    public JwtUserDetails(Long id, String username, String password, UserType userType, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.authorities = authorities;
    }

    /**
     * Erzeugt eine Instanz von JwtUserDetails für einen privaten Benutzer (Endnutzer).
     */
    public static JwtUserDetails build(UserModel user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_PRIVATEUSER"));
                return new JwtUserDetails(
                        user.getId(),
                        user.getName(),
                        user.getPassword(),
                        user.getUserType(),
                        authorities
                );
    }

    /**
     * Erzeugt eine Instanz von JwtUserDetails für eine Firma.
     */
    public static JwtUserDetails build(CompanyModel company) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_COMPANYUSER"));
        return new JwtUserDetails(
                company.getId(),
                company.getCompanyName(),
                company.getPassword(),
                UserType.COMPANYUSER,
                authorities
        );
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
