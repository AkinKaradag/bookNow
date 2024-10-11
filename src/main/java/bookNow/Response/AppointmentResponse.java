package bookNow.Response;

import bookNow.Model.AppointmentModel;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentResponse {

    long appointmentId;
    long userId;
    long serviceId;
    String serviceName;
    long companyId;
    String companyName;
    LocalDate appointmentDate;
    LocalTime appointmentTime;

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
