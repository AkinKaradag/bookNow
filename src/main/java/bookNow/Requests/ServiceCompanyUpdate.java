package bookNow.Requests;

/**
 * Repräsentiert die Daten, die zur Aktualisierung eines Service in einem Unternehmen erforderlich sind,
 * wie Service-Name, Beschreibung, Preis und Dauer.
 */
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceCompanyUpdate {

    private String name; // Neuer Name des Services
    private String description; // Neue Beschreibung des Services
    private BigDecimal price; // Neuer Preis des Services
    private int duration; // Neue Dauer des Services in Minuten


}
