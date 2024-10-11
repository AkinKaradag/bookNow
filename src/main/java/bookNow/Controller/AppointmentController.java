package bookNow.Controller;

import bookNow.Model.AppointmentModel;
import bookNow.Requests.AppointmentCreateRequest;
import bookNow.Requests.AppointmentUpdateRequest;
import bookNow.Response.AppointmentResponse;
import bookNow.Security.JwtUserDetails;
import bookNow.Service.AppointmentService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;


    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentCreateRequest newAppointment, Authentication authentication) {
        try {
            // Extrahiere die Benutzer ID aus dem Authentication-Objekt
            Long authenticatedUserId = null;
            if (authentication.getPrincipal() instanceof JwtUserDetails) {
                JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
                authenticatedUserId = userDetails.getId();
            } else {
                throw new IllegalArgumentException("Principal ist kein JwtUserDetails-Objekt");
            }

            // Füge die userId des Authentifizierten zur Anfrage hinzu, falls nicht gesetzt
            if (newAppointment.getUserId() == null) {
                newAppointment.setUserId(authenticatedUserId);
            }


            AppointmentModel createdAppointment = appointmentService.createAppointment(newAppointment);
            System.out.println("Erstellter Termin: " + createdAppointment);
            return ResponseEntity.ok(createdAppointment);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    @GetMapping
    public List<AppointmentResponse> getAllAppointments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> serviceId, @RequestParam Optional <Long> companyId) {
        return appointmentService.getAllAppointments(userId, serviceId, companyId);
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
