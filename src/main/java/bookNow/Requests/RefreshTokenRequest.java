package bookNow.Requests;

import bookNow.Model.CompanyModel;
import bookNow.Model.UserModel;
import bookNow.Model.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Repräsentiert eine Anfrage zum Aktualisieren des Refresh-Tokens,
 * wobei die Benutzer-ID oder Unternehmens-ID, das Token und der Benutzertyp (Benutzer oder Unternehmen) enthalten sind.
 */
@Data
public class RefreshTokenRequest {
    private Long id; // Benutzer- oder Unternehmens-ID, je nach Benutzertyp
    private String refreshToken; // Refresh-Token
    @JsonProperty("isCompanyUser")
    private boolean isCompanyUser; // Gibt an, ob es sich um ein Unternehmens-Token handelt
    private UserModel user;
    private CompanyModel company;
    private String token; // Token

}
