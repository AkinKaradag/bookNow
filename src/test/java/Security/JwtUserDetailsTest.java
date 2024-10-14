package Security;

import bookNow.Model.CompanyModel;
import bookNow.Model.UserModel;
import bookNow.Model.UserType;
import bookNow.Security.JwtUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse für JwtUserDetails, um die verschiedenen Szenarien zu testen, in denen Benutzerinformationen erstellt werden.
 */
public class JwtUserDetailsTest {

    private UserModel user;
    private CompanyModel company;

    @BeforeEach
    public void setUp() {
        // Initialisiere ein UserModel und ein CompanyModel für die Tests
        user = new UserModel();
        user.setId(1L);
        user.setName("testuser");
        user.setPassword("password");
        user.setUserType(UserType.PRIVATEUSER);

        company = new CompanyModel();
        company.setId(2L);
        company.setCompanyName("testcompany");
        company.setPassword("password");
    }

    @Test
    public void testBuildPrivateUser() {
        // Erzeuge JwtUserDetails für einen privaten Benutzer
        JwtUserDetails userDetails = JwtUserDetails.build(user);

        // Überprüfe, ob die Benutzerinformationen korrekt gesetzt sind
        assertEquals(user.getId(), userDetails.getId());
        assertEquals(user.getName(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(user.getUserType(), userDetails.getUserType());

        // Überprüfe, ob die Autoritäten korrekt gesetzt sind
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_PRIVATEUSER")));
    }

    @Test
    public void testBuildCompanyUser() {
        // Erzeuge JwtUserDetails für eine Firma
        JwtUserDetails userDetails = JwtUserDetails.build(company);

        // Überprüfe, ob die Firmeninformationen korrekt gesetzt sind
        assertEquals(company.getid(), userDetails.getId());
        assertEquals(company.getCompanyName(), userDetails.getUsername());
        assertEquals(company.getPassword(), userDetails.getPassword());
        assertEquals(UserType.COMPANYUSER, userDetails.getUserType());

        // Überprüfe, ob die Autoritäten korrekt gesetzt sind
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_COMPANYUSER")));
    }

    @Test
    public void testJwtUserDetailsConstructor() {
        // Erstelle eine Liste von Autoritäten
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_PRIVATEUSER"));

        // Erzeuge JwtUserDetails über den Konstruktor
        JwtUserDetails userDetails = new JwtUserDetails(3L, "testuser3", "password3", UserType.PRIVATEUSER, authorities);

        // Überprüfe, ob die Benutzerinformationen korrekt gesetzt sind
        assertEquals(3L, userDetails.getId());
        assertEquals("testuser3", userDetails.getUsername());
        assertEquals("password3", userDetails.getPassword());
        assertEquals(UserType.PRIVATEUSER, userDetails.getUserType());
        assertEquals(authorities, userDetails.getAuthorities());
    }
}
