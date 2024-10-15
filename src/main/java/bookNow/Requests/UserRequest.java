package bookNow.Requests;

import bookNow.Model.UserType;
import lombok.Data;

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

    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }


    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public UserType getUserType() {
        return this.userType;
    }


}
