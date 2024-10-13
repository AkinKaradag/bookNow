package bookNow.Response;

import bookNow.Model.AppointmentModel;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Dient als Antwortklasse für Termindaten. Enthält Informationen zu Termin, Benutzer, Service und Firma.
 * Die Felder werden direkt aus einem AppointmentModel-Objekt initialisiert.
 */
@Data
public class AppointmentResponse {

    long appointmentId; // ID des Termins
    long userId; // ID des Benutzers, der den Termin gebucht hat
    long serviceId; // ID des gebuchten Services
    String serviceName; // Name des gebuchten Services
    long companyId; // ID der Firma, die den Service anbietet
    String companyName; // Name der Firma
    LocalDate appointmentDate; // Datum des Termins
    LocalTime appointmentTime; // Uhrzeit des Termins

    // Konstruktor zur Initialisierung der Antwortdaten aus einem AppointmentModel-Objekt
    public AppointmentResponse (AppointmentModel entity) {
        this.appointmentId = entity.getAppointmentId();
        this.userId = entity.getUser().getId();
        this.serviceId = entity.getService().getServiceId();
        this.serviceName = entity.getService().getName();
        this.companyId = entity.getCompany().getid();
        this.companyName = entity.getCompany().getCompanyName();
        this.appointmentDate = entity.getAppointmentDate();
        this.appointmentTime = entity.getAppointmentTime();
    }

}
