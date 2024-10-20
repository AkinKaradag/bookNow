package bookNow.Response;

import bookNow.Model.UserType;
import lombok.Data;

/**
 * Antwortklasse für Login-Anfragen, die die Authentifizierungsergebnisse enthält,
 * wie Benutzer-ID, Benutzertyp und die Zugriffstoken.
 */
@Data
public class LoginResponse {
    private String message; // Nachricht zur Authentifizierung
    private Long id; // ID des Benutzers oder Unternehmens
    private UserType userType; // Typ des Benutzers (z.B. PRIVATEUSER, COMPANYUSER)
    private String accessToken; // JWT-Zugriffstoken
    private String refreshToken; // Refresh-Token


}

