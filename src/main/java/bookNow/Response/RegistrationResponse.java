package bookNow.Response;

import bookNow.Model.UserType;
import lombok.Data;

/**
 * Antwortklasse für Registrierungsanfragen, die die Registrierungsergebnisse enthält,
 * wie Benutzer-ID und Benutzertyp.
 */
@Data
public class RegistrationResponse {
    String message; // Nachricht zur Registrierung
    Long id; // ID des Benutzers oder Unternehmens
    UserType userType; // Typ des Benutzers (z.B. PRIVATEUSER, COMPANYUSER)
}

