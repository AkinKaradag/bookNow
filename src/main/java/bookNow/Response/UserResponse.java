package bookNow.Response;

import bookNow.Model.UserModel;
import lombok.Data;

/**
 * Antwortklasse für Benutzerinformationen, die relevante Daten eines Benutzers enthält.
 * Die Felder werden direkt aus einem UserModel-Objekt initialisiert.
 */
@Data
public class UserResponse {

    private Long userId; // ID des Benutzers
    private String userName; // Name des Benutzers
    private String email; // E-Mail-Adresse des Benutzers
    private String password; // Passwort des Benutzers

    // Konstruktor zur Initialisierung der Antwortdaten aus einem UserModel-Objekt
    public UserResponse(UserModel entity){
        this.userId = entity.getId();
        this.userName = entity.getName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
    }
}
