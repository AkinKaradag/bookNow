package bookNow.Requests;

import bookNow.Model.UserType;
import lombok.Data;

/**
 * Repräsentiert die Daten, die bei der Registrierung eines Unternehmenskontos benötigt werden.
 * Enthält Unternehmensname, Adresse, Stadt, PLZ, Telefonnummer, Beschreibung, Passwort und Benutzertyp.
 */
@Data
public class CompanyRequest {
    private String companyName; // Name des Unternehmens
    private String companyAddress; // Adresse des Unternehmens
    private String companyCity; // Stadt, in der das Unternehmen ansässig ist
    private int companyPostalCode; // PLZ des Unternehmens
    private String phoneNumber; // Telefonnummer des Unternehmens
    private String description; // Beschreibung des Unternehmens
    private String password; // Passwort des Unternehmenskontos
    private UserType userType; // Benutzertyp des Unternehmens (z.B. COMPANYUSER)
}
