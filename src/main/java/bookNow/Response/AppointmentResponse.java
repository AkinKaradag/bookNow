package bookNow.Response;

import bookNow.Model.AppointmentModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Dient als Antwortklasse für Termindaten. Enthält Informationen zu Termin, Benutzer, Service und Firma.
 * Die Felder werden direkt aus einem AppointmentModel-Objekt initialisiert.
 */
@Data
public class AppointmentResponse {

    private long appointmentId; // ID des Termins
    private long userId; // ID des Benutzers, der den Termin gebucht hat
    private long serviceId; // ID des gebuchten Services
    private  String serviceName; // Name des gebuchten Services
    private long companyId; // ID der Firma, die den Service anbietet
    private String companyName; // Name der Firma
    private LocalDate appointmentDate; // Datum des Termins
    private LocalTime appointmentTime; // Uhrzeit des Termins

    // Konstruktor zur Initialisierung der Antwortdaten aus einem AppointmentModel-Objekt
    public AppointmentResponse (AppointmentModel entity) {
        this.appointmentId = entity.getAppointmentId();
        this.userId = entity.getUser().getId();
        this.serviceId = entity.getService().getServiceId();
        this.serviceName = entity.getService().getName();
        this.companyId = entity.getCompany().getId();
        this.companyName = entity.getCompany().getCompanyName();
        this.appointmentDate = entity.getAppointmentDate();
        this.appointmentTime = entity.getAppointmentTime();
    }

}
