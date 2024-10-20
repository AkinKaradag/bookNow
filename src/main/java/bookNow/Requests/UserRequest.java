package bookNow.Requests;

import bookNow.Model.UserType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Repräsentiert die Daten, die bei der Registrierung eines Benutzers erforderlich sind,
 * wie Name, E-Mail, Passwort und Benutzertyp.
 */
@Data
public class UserRequest {

    private String name; // Name des Benutzers
    private String email; // E-Mail-Adresse des Benutzers
    private String password; // Passwort des Benutzers
    private UserType userType; // Benutzertyp (z.B. PRIVATEUSER)


}
