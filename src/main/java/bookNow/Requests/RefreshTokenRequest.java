package bookNow.Requests;

import lombok.Data;

/**
 * Repräsentiert eine Anfrage zum Aktualisieren des Refresh-Tokens,
 * wobei die Benutzer-ID oder Unternehmens-ID, das Token und der Benutzertyp (Benutzer oder Unternehmen) enthalten sind.
 */
@Data
public class RefreshTokenRequest {
    private Long id; // Benutzer- oder Unternehmens-ID, je nach Benutzertyp
    private String refreshToken; // Refresh-Token
    private boolean isCompanyUser; // Gibt an, ob es sich um ein Unternehmens-Token handelt
}
