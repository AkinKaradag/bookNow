package bookNow.Requests;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Repräsentiert die Daten, die für die Erstellung eines Service in einem Unternehmen benötigt werden,
 * einschließlich des Service-Namens, der Beschreibung, des Preises und der Dauer.
 */
@Data
public class ServiceCompanyRequest {

    private Long serviceId; // ID des Services
    private String name; // Name des Services
    private String description; // Beschreibung des Services
    private BigDecimal price; // Preis des Services
    private int duration; // Dauer des Services in Minuten
    private Long companyId; // Unternehmens-ID, die den Service anbietet

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setDuration(int duration) { this.duration = duration; }


    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}