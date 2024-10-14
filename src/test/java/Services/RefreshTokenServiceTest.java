package Services;

import bookNow.Model.CompanyModel;
import bookNow.Model.RefreshTokenModel;
import bookNow.Model.UserModel;
import bookNow.Repository.RefreshTokenRepository;
import bookNow.Service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Diese Testklasse validiert die Funktionalitaet des RefreshTokenService.
 */
class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        // Setze expireSeconds manuell, da die @Value-Injektion im Test nicht funktioniert
        Field expireSecondsField = RefreshTokenService.class.getDeclaredField("expireSeconds");
        expireSecondsField.setAccessible(true);
        expireSecondsField.set(refreshTokenService, 3600L);
    }

    /**
     * Testet die Erstellung eines neuen Refresh Tokens fuer einen Benutzer.
     */
    @Test
    void testCreateRefreshToken_ForUser() {
        UserModel user = new UserModel();
        user.setId(1L);

        RefreshTokenModel tokenModel = new RefreshTokenModel();
        tokenModel.setUser(user);
        tokenModel.setToken(UUID.randomUUID().toString());
        tokenModel.setExpiryDate(Date.from(Instant.now().plusSeconds(3600)));

        when(refreshTokenRepository.findByUser(user)).thenReturn(null);
        when(refreshTokenRepository.save(any(RefreshTokenModel.class))).thenReturn(tokenModel);

        String token = refreshTokenService.createRefreshToken(user, null);

        assertNotNull(token);
        verify(refreshTokenRepository, times(1)).save(any(RefreshTokenModel.class));
    }

    /**
     * Testet die Erstellung eines neuen Refresh Tokens fuer eine Firma.
     */
    @Test
    void testCreateRefreshToken_ForCompany() {
        CompanyModel company = new CompanyModel();
        company.setId(1L);

        RefreshTokenModel tokenModel = new RefreshTokenModel();
        tokenModel.setCompany(company);
        tokenModel.setToken(UUID.randomUUID().toString());
        tokenModel.setExpiryDate(Date.from(Instant.now().plusSeconds(3600)));

        when(refreshTokenRepository.findByCompany(company)).thenReturn(null);
        when(refreshTokenRepository.save(any(RefreshTokenModel.class))).thenReturn(tokenModel);

        String token = refreshTokenService.createRefreshToken(null, company);

        assertNotNull(token);
        verify(refreshTokenRepository, times(1)).save(any(RefreshTokenModel.class));
    }

    /**
     * Testet, ob ein Refresh Token abgelaufen ist.
     */
    @Test
    void testIsRefreshExpired() {
        RefreshTokenModel token = new RefreshTokenModel();
        token.setExpiryDate(Date.from(Instant.now().minusSeconds(3600)));

        boolean isExpired = refreshTokenService.isRefreshExpired(token);

        assertTrue(isExpired);
    }

    /**
     * Testet den Abruf eines Refresh Tokens basierend auf der Benutzer-ID.
     */
    @Test
    void testGetByUserOrCompany_ForUser() {
        UserModel user = new UserModel();
        user.setId(1L);

        RefreshTokenModel tokenModel = new RefreshTokenModel();
        tokenModel.setUser(user);

        when(refreshTokenRepository.findByUser_Id(1L)).thenReturn(tokenModel);

        RefreshTokenModel result = refreshTokenService.getByUserOrCompany(1L, false);

        assertNotNull(result);
        assertEquals(user, result.getUser());
    }

    /**
     * Testet den Abruf eines Refresh Tokens basierend auf der Firmen-ID.
     */
    @Test
    void testGetByUserOrCompany_ForCompany() {
        CompanyModel company = new CompanyModel();
        company.setId(1L);

        RefreshTokenModel tokenModel = new RefreshTokenModel();
        tokenModel.setCompany(company);

        when(refreshTokenRepository.findByCompany_Id(1L)).thenReturn(tokenModel);

        RefreshTokenModel result = refreshTokenService.getByUserOrCompany(1L, true);

        assertNotNull(result);
        assertEquals(company, result.getCompany());
    }
}
