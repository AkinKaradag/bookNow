package bookNow.Service;

import bookNow.Model.CompanyModel;
import bookNow.Model.RefreshTokenModel;
import bookNow.Model.UserModel;
import bookNow.Model.UserType;
import bookNow.Repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * Diese Service-Klasse verwaltet die Erstellung und Validierung von Refresh Tokens.
 */
@Service
public class RefreshTokenService {
    @Value("${refresh.token.expires.in}")
    private Long expireSeconds;

    private final RefreshTokenRepository refreshTokenRepository;

    // Konstruktor für die Abhängigkeitsinjektion des Repositories
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // Methode zum Erstellen eines neuen Refresh Tokens für einen User oder ein Unternehmen
    public String createRefreshToken(UserModel user, CompanyModel company) {
        RefreshTokenModel token;

        if (user != null) {
            token = refreshTokenRepository.findByUser(user);
            if (token == null) {
                token = new RefreshTokenModel();
                token.setUser(user);
            }
            token.setUserType(UserType.PRIVATEUSER);
        } else if (company != null) {
            token = refreshTokenRepository.findByCompany(company);
            if (token == null) {
                token = new RefreshTokenModel();
                token.setCompany(company);
            }
            token.setUserType(UserType.COMPANYUSER);
        } else {
            throw new IllegalArgumentException("Either user or company must be specified.");
        }

        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
        refreshTokenRepository.save(token);
        return token.getToken();
    }

    // Überprüfung, ob ein Refresh Token abgelaufen ist
    public boolean isRefreshExpired(RefreshTokenModel token) {
        return token.getExpiryDate().before(new Date());
    }

    // Abruf des Refresh Tokens basierend auf der Benutzer-ID oder der Company-ID
    public RefreshTokenModel getByUserOrCompany(Long id, boolean isCompanyUser) {
        return isCompanyUser ? refreshTokenRepository.findByCompany_Id(id) : refreshTokenRepository.findByUser_Id(id);
    }
}

