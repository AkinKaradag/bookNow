package bookNow.Response;

import bookNow.Model.UserType;
import lombok.Data;

/**
 * Antwortklasse für Registrierungsanfragen, die die Registrierungsergebnisse enthält,
 * wie Benutzer-ID und Benutzertyp.
 */
@Data
public class RegistrationResponse {
    private String message; // Nachricht zur Registrierung
    private Long id; // ID des Benutzers oder Unternehmens
    private UserType userType; // Typ des Benutzers (z.B. PRIVATEUSER, COMPANYUSER)


}

