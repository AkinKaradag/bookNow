package bookNow.requests;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentUpdateRequest {
    LocalDate appointmentDateRequest;
    LocalTime appointmentTimeRequest;

    public LocalDate getAppointmentDateRequest() {
        return appointmentDateRequest;
    }

    public void setAppointmentDateRequest(LocalDate appointmentDate) {
        this.appointmentDateRequest = appointmentDate;
    }

    public LocalTime getAppointmentTimeRequest() {
        return appointmentTimeRequest;
    }

    public void setAppointmentTimeRequest(LocalTime appointmentTime) {
        this.appointmentTimeRequest = appointmentTime;
    }
}


