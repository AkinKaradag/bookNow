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

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setDuration(int duration) { this.duration = duration; }


    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Integer getDuration() {
        return this.duration;
    }



}
