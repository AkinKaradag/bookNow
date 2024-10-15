package bookNow.Response;

import bookNow.Model.CompanyModel;
import lombok.Data;

/**
 * Antwortklasse für Firmeninformationen, die relevante Daten eines Unternehmens enthält.
 * Die Felder werden direkt aus einem CompanyModel-Objekt initialisiert.
 */
@Data
public class CompanyResponse {

    Long companyId; // ID der Firma
    String companyName; // Name der Firma
    String companyDescription; // Beschreibung der Firma
    String companyAddress; // Adresse der Firma
    String companyCity; // Stadt, in der die Firma ansässig ist
    int companyPLZ; // Postleitzahl der Firma
    String companyPhone; // Telefonnummer der Firma
    String password; // Passwort der Firma

    // Konstruktor zur Initialisierung der Antwortdaten aus einem CompanyModel-Objekt
    public CompanyResponse(CompanyModel entity){
        this.companyId = entity.getId();
        this.companyName = entity.getCompanyName();
        this.companyDescription = entity.getDescription();
        this.companyAddress = entity.getCompanyAddress();
        this.companyCity = entity.getCompanyCity();
        this.companyPLZ = entity.getCompanyPostalCode();
        this.companyPhone = entity.getPhoneNumber();
        this.password = entity.getPassword();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
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

    public int getCompanyPLZ() {
        return companyPLZ;
    }

    public void setCompanyPLZ(int companyPLZ) {
        this.companyPLZ = companyPLZ;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
