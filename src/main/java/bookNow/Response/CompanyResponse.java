package bookNow.Response;

import bookNow.Model.CompanyModel;
import lombok.Data;

/**
 * Antwortklasse für Firmeninformationen, die relevante Daten eines Unternehmens enthält.
 * Die Felder werden direkt aus einem CompanyModel-Objekt initialisiert.
 */
@Data
public class CompanyResponse {

    private Long companyId; // ID der Firma
    private String companyName; // Name der Firma
    private String companyDescription; // Beschreibung der Firma
    private String companyAddress; // Adresse der Firma
    private String companyCity; // Stadt, in der die Firma ansässig ist
    private int companyPLZ; // Postleitzahl der Firma
    private String companyPhone; // Telefonnummer der Firma
    private String password; // Passwort der Firma

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


}
