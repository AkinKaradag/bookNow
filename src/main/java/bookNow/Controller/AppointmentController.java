package bookNow.Controller;

import bookNow.Model.AppointmentModel;
import bookNow.Service.AppointmentService;
import bookNow.requests.AppointmentCreateRequest;
import bookNow.requests.AppointmentUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public AppointmentModel createAppointment(@RequestBody AppointmentCreateRequest newAppointment) {
        return appointmentService.createAppointment(newAppointment);
    }

    @GetMapping
    public List<AppointmentModel> getAllAppointments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> companyId, @RequestParam Optional<Long> serviceId) {
        return appointmentService.getAllAppointments(userId, companyId, serviceId);
    }

    @GetMapping("/{appointmentId}")
    public AppointmentModel findByAppointmentId(@PathVariable Long appointmentId) {
        //custom exception
        return appointmentService.findByAppointmentId(appointmentId);
    }

    @PutMapping("/{appointmentId}")
    public AppointmentModel updateAppointment(@PathVariable Long appointmentId, @RequestBody AppointmentUpdateRequest updatedAppointment) {
        return appointmentService.updateAppointment(appointmentId, updatedAppointment);

    }

    @DeleteMapping("/{appointmentId}")
    public void deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
    }


    // Weitere Endpunkte für Update, Delete usw.

}
