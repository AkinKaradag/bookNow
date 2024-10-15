package bookNow.Requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

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
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    public void setAppointmentDateRequest(LocalDate date) { this.appointmentDate = date; }
    public void setAppointmentTimeRequest(LocalTime time) { this.appointmentTime = time; }



    // Neue Uhrzeit für den Termin
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime appointmentTimeRequest;

    public LocalDate getAppointmentDateRequest() {
        return this.appointmentDateRequest;
    }

    public LocalTime getAppointmentTimeRequest() {
        return this.appointmentTimeRequest;
    }



}


