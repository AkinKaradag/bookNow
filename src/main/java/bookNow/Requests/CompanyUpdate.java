package bookNow.Requests;

import bookNow.Model.UserType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Repräsentiert die Daten, die zur Aktualisierung eines Unternehmenskontos erforderlich sind.
 * Enthält Felder wie Firmenname, Adresse, Stadt, PLZ, Telefonnummer, Beschreibung, Passwort und Benutzertyp.
 */
@Data
public class CompanyUpdate {

    private String companyName;
    private String companyAddress;
    private String companyCity;
    private int companyPostalCode;
    private String phoneNumber;
    private String description;
    private String password;
    private UserType userType; // Benutzertyp des Unternehmens (z.B. COMPANYUSER)


}
