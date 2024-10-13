package bookNow.Response;

import bookNow.Model.ServiceCompanyModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Antwortklasse für Serviceinformationen, die relevante Daten zu einem Service und der dazugehörigen Firma enthält.
 * Die Felder werden direkt aus einem ServiceCompanyModel-Objekt initialisiert.
 */
@Data
public class ServiceCompanyResponse {

    Long serviceId; // ID des Services
    Long companyId; // ID der Firma, die den Service anbietet
    String companyName; // Name der Firma
    String name; // Name des Services
    String description; // Beschreibung des Services
    BigDecimal price; // Preis des Services
    int duration; // Dauer des Services in Minuten

    // Konstruktor zur Initialisierung der Antwortdaten aus einem ServiceCompanyModel-Objekt
    public ServiceCompanyResponse(ServiceCompanyModel entity){
        this.serviceId = entity.getServiceId();
        this.companyId = entity.getCompany().getid();
        this.companyName = entity.getCompany().getCompanyName();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.duration = entity.getDuration();
    }

}
