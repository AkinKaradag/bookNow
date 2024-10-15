package bookNow.Response;

import bookNow.Model.UserModel;
import lombok.Data;

/**
 * Antwortklasse für Benutzerinformationen, die relevante Daten eines Benutzers enthält.
 * Die Felder werden direkt aus einem UserModel-Objekt initialisiert.
 */
@Data
public class UserResponse {

    Long userId; // ID des Benutzers
    String userName; // Name des Benutzers
    String email; // E-Mail-Adresse des Benutzers
    String password; // Passwort des Benutzers

    // Konstruktor zur Initialisierung der Antwortdaten aus einem UserModel-Objekt
    public UserResponse(UserModel entity){
        this.userId = entity.getId();
        this.userName = entity.getName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
