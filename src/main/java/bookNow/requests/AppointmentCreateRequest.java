package bookNow.requests;


import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentCreateRequest {

    private Long appointmentIdRequest;
    private LocalDate appointmentDateRequest;
    private LocalTime appointmentTimeRequest;
    private Long companyIdRequest;
    private Long serviceIdRequest;
    private Long userIdRequest;

    public Long getAppointmentIdRequest() {
        return appointmentIdRequest;
    }

    public void setAppointmentIdRequest(Long appointmentId) {
        this.appointmentIdRequest = appointmentId;
    }

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

    public Long getCompanyIdRequest() {
        return companyIdRequest;
    }

    public void setCompanyIdRequest(Long companyId) {
        this.companyIdRequest = companyId;
    }

    public Long getServiceIdRequest() {
        return serviceIdRequest;
    }

    public void setServiceIdRequest(Long serviceId) {
        this.serviceIdRequest = serviceId;
    }

    public Long getUserIdRequest() {
        return userIdRequest;
    }

    public void setUserIdRequest(Long userId) {
        this.userIdRequest = userId;
    }
}
