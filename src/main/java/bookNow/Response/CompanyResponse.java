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
        this.companyId = entity.getid();
        this.companyName = entity.getCompanyName();
        this.companyDescription = entity.getDescription();
        this.companyAddress = entity.getCompanyAddress();
        this.companyCity = entity.getCompanyCity();
        this.companyPLZ = entity.getCompanyPostalCode();
        this.companyPhone = entity.getPhoneNumber();
        this.password = entity.getPassword();
    }
}
