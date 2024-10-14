package Services;

import bookNow.Model.CompanyModel;
import bookNow.Model.UserModel;
import bookNow.Model.UserType;
import bookNow.Repository.CompanyRepository;
import bookNow.Repository.UserRepository;
import bookNow.Security.JwtUserDetails;
import bookNow.Service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.NoSuchElementException;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testklasse für UserDetailsServiceImpl, die sicherstellt, dass die Benutzer- und Firmeninformationen korrekt geladen werden.
 */
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        // Arrange
        UserModel user = new UserModel();
        user.setId(1L);
        user.setName("testuser");
        user.setPassword("password");
        user.setUserType(UserType.PRIVATEUSER);

        when(userRepository.findByName("testuser")).thenReturn(user);

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        verify(userRepository, times(1)).findByName("testuser");
        verify(companyRepository, never()).findByCompanyName(anyString());
    }

    @Test
    public void testLoadUserByUsername_CompanyFound() {
        // Arrange
        CompanyModel company = new CompanyModel();
        company.setId(2L);
        company.setCompanyName("testcompany");
        company.setPassword("password");

        when(companyRepository.findByCompanyName("testcompany")).thenReturn(company);

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername("testcompany");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testcompany", userDetails.getUsername());
        verify(companyRepository, times(1)).findByCompanyName("testcompany");
        verify(userRepository, times(1)).findByName("testcompany");
    }

    @Test
    public void testLoadUserByUsername_NotFound() {
        // Arrange
        when(userRepository.findByName("unknown")).thenReturn(null);
        when(companyRepository.findByCompanyName("unknown")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("unknown"));
        verify(userRepository, times(1)).findByName("unknown");
        verify(companyRepository, times(1)).findByCompanyName("unknown");
    }

    @Test
    public void testLoadUserById_UserFound() {
        // Arrange
        UserModel user = new UserModel();
        user.setId(1L);
        user.setName("testuser");
        user.setPassword("password");
        user.setUserType(UserType.PRIVATEUSER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userDetailsService.loadUserById(1L);

        // Assert
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testLoadUserById_UserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> userDetailsService.loadUserById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testLoadCompanyById_CompanyFound() {
        // Arrange
        CompanyModel company = new CompanyModel();
        company.setId(2L);
        company.setCompanyName("testcompany");
        company.setPassword("password");

        when(companyRepository.findById(2L)).thenReturn(Optional.of(company));

        // Act
        UserDetails userDetails = userDetailsService.loadCompanyById(2L);

        // Assert
        assertNotNull(userDetails);
        assertEquals("testcompany", userDetails.getUsername());
        verify(companyRepository, times(1)).findById(2L);
    }

    @Test
    public void testLoadCompanyById_CompanyNotFound() {
        // Arrange
        when(companyRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> userDetailsService.loadCompanyById(2L));
        verify(companyRepository, times(1)).findById(2L);
    }
}

