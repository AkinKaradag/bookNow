package bookNow.Requests;

import bookNow.Model.CompanyModel;
import bookNow.Model.UserModel;
import lombok.Data;

/**
 * Repräsentiert eine Anfrage zum Aktualisieren des Refresh-Tokens,
 * wobei die Benutzer-ID oder Unternehmens-ID, das Token und der Benutzertyp (Benutzer oder Unternehmen) enthalten sind.
 */
@Data
public class RefreshTokenRequest {
    private Long id; // Benutzer- oder Unternehmens-ID, je nach Benutzertyp
    private String refreshToken; // Refresh-Token
    private boolean isCompanyUser; // Gibt an, ob es sich um ein Unternehmens-Token handelt
    private UserModel user;
    private CompanyModel company;
    private String token; // Token


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean isCompanyUser() {
        return isCompanyUser;
    }

    public void setCompanyUser(boolean companyUser) {
        isCompanyUser = companyUser;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public CompanyModel getCompany() {
        return company;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
