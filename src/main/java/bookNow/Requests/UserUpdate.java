package bookNow.Requests;

import lombok.Data;

/**
 * Repräsentiert die Daten, die für die Aktualisierung eines Benutzerkontos erforderlich sind,
 * wie Benutzername, E-Mail und Passwort.
 */
@Data
public class UserUpdate {

        private String userName; // Neuer Name des Benutzers
        private String email; // Neue E-Mail-Adresse des Benutzers
        private String password; // Neues Passwort des Benutzers
}
