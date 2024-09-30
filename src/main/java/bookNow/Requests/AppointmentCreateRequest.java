package bookNow.Requests;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentCreateRequest {

    @JsonIgnoreProperties(ignoreUnknown = true)
    private Long appointmentId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate appointmentDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime appointmentTime;
    private Long companyId;
    private Long serviceId;
    private Long userId;

}
