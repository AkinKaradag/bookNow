package bookNow.Requests;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Repräsentiert die Daten, die für die Erstellung eines Termins benötigt werden.
 * Enthält Informationen wie das Datum, die Uhrzeit und die IDs von Unternehmen, Service und Benutzer.
 */
@Data
public class AppointmentCreateRequest {

    private Long appointmentId;
    // Datum des Termins im Format "dd-MM-yyyy"
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate appointmentDate;
    // Uhrzeit des Termins im Format "HH:mm"
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime appointmentTime;
    private Long companyId; // Unternehmens-ID, bei der der Termin gebucht wird
    private Long serviceId; // Service-ID, die für den Termin gebucht wird
    private Long userId; // Benutzer-ID, die den Termin bucht

}
