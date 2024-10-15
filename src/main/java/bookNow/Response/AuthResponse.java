package bookNow.Response;

import bookNow.Model.UserType;
import lombok.Data;

/**
 * Antwortklasse für Authentifizierungsanfragen, die die Authentifizierungsergebnisse enthält,
 * wie Benutzer-ID, Benutzertyp und die Zugriffstoken.
 */
@Data
public class AuthResponse {

    String message; // Nachricht zur Authentifizierung
    Long id; // ID des Benutzers oder Unternehmens
    UserType userType; // Typ des Benutzers (z.B. PRIVATEUSER, COMPANYUSER)
    String accessToken; // JWT-Zugriffstoken
    String refreshToken; // Refresh-Token

    public String getMessage() { return this.message; }
    public String getAccessToken() { return this.accessToken; }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
