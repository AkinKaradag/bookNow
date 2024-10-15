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
