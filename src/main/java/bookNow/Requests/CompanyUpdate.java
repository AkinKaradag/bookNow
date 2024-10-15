package bookNow.Requests;

import bookNow.Model.UserType;
import lombok.Data;

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

    public void setCompanyName(String name) { this.companyName = name; }
    public void setPassword(String password) { this.password = password; }




    public String getCompanyName() {
        return this.companyName;
    }

    public String getCompanyAddress() {
        return this.companyAddress;
    }

    public String getCompanyCity() {
        return this.companyCity;
    }

    public int getCompanyPostalCode() {
        return this.companyPostalCode;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPassword() {
        return this.password;
    }

    public UserType getUserType() {
        return this.userType;
    }



}
