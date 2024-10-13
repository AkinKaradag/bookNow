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

/**
 * Controller für die Verwaltung von Terminen.
 */

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    /**
     * Erstellt einen neuen Termin.
     * @param newAppointment Die Details des neuen Termins.
     * @param authentication Die Authentifizierung des aktuellen Benutzers.
     * @return Eine ResponseEntity mit dem erstellten Termin oder einer Fehlermeldung.
     */
    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentCreateRequest newAppointment, Authentication authentication) {
        try {
            // Benutzer ID aus Authentication-Objekt extrahieren
            Long authenticatedUserId = null;
            if (authentication.getPrincipal() instanceof JwtUserDetails) {
                JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
                authenticatedUserId = userDetails.getId();
            } else {
                throw new IllegalArgumentException("Principal ist kein JwtUserDetails-Objekt");
            }

            // Setzt die Benutzer ID, falls sie noch nicht gesetzt ist
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

    /**
     * Ruft eine Liste aller Termine ab, optional gefiltert nach Benutzer-ID, Service-ID oder Firmen-ID.
     * @return Eine Liste von AppointmentResponse-Objekten.
     */
    @GetMapping
    public List<AppointmentResponse> getAllAppointments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> serviceId, @RequestParam Optional <Long> companyId) {
        return appointmentService.getAllAppointments(userId, serviceId, companyId);
    }

    /**
     * Ruft einen Termin anhand seiner ID ab.
     * @param appointmentId Die ID des Termins.
     * @return Das AppointmentModel-Objekt des Termins.
     */
    @GetMapping("/{appointmentId}")
    public AppointmentModel findByAppointmentId(@PathVariable Long appointmentId) {
        //custom exception
        return appointmentService.findByAppointmentId(appointmentId);
    }

    /**
     * Aktualisiert die Daten eines Termins.
     * @param appointmentId Die ID des zu aktualisierenden Termins.
     * @param updatedAppointment Die neuen Termindaten.
     * @return Das aktualisierte AppointmentModel-Objekt.
     */
    @PutMapping("/{appointmentId}")
    public AppointmentModel updateAppointment(@PathVariable Long appointmentId, @RequestBody AppointmentUpdateRequest updatedAppointment) {
        return appointmentService.updateAppointment(appointmentId, updatedAppointment);

    }

    /**
     * Löscht einen Termin anhand seiner ID.
     * @param appointmentId Die ID des zu löschenden Termins.
     */
    @DeleteMapping("/{appointmentId}")
    public void deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
    }
}
