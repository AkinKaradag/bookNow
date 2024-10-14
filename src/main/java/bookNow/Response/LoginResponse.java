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
}

