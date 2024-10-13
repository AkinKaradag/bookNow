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

}