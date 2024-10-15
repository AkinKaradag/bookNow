package bookNow.Requests;


import com.fasterxml.jackson.annotation.JsonFormat;
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

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
