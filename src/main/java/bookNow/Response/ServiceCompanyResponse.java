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
        this.companyId = entity.getCompany().getId();
        this.companyName = entity.getCompany().getCompanyName();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.duration = entity.getDuration();
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
