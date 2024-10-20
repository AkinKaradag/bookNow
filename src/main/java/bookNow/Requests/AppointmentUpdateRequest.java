package bookNow.Requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 * Repräsentiert die Daten, die für die Aktualisierung eines Termins erforderlich sind,
 * wie das neue Datum und die neue Uhrzeit.
 */
@Data
public class AppointmentUpdateRequest {
    // Neues Datum für den Termin
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate appointmentDateRequest;

    // Neue Uhrzeit für den Termin
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime appointmentTimeRequest;


}


