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


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public int getCompanyPostalCode() {
        return companyPostalCode;
    }

    public void setCompanyPostalCode(int companyPostalCode) {
        this.companyPostalCode = companyPostalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
