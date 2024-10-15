package bookNow.Response;

import bookNow.Model.UserType;
import lombok.Data;

/**
 * Antwortklasse für Login-Anfragen, die die Authentifizierungsergebnisse enthält,
 * wie Benutzer-ID, Benutzertyp und die Zugriffstoken.
 */
@Data
public class LoginResponse {
    String message; // Nachricht zur Authentifizierung
    Long id; // ID des Benutzers oder Unternehmens
    UserType userType; // Typ des Benutzers (z.B. PRIVATEUSER, COMPANYUSER)
    String accessToken; // JWT-Zugriffstoken
    String refreshToken; // Refresh-Token

    public String getAccessToken() { return this.accessToken; }
    public String getRefreshToken() { return this.refreshToken; }
    public Long getId() { return this.id; }
    public UserType getUserType() { return this.userType; }


    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


}

