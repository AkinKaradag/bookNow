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


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}

