package Controller;

import bookNow.Controller.AppointmentController;
import bookNow.Model.AppointmentModel;
import bookNow.Model.CompanyModel;
import bookNow.Model.ServiceCompanyModel;
import bookNow.Model.UserModel;
import bookNow.Requests.AppointmentCreateRequest;
import bookNow.Requests.AppointmentUpdateRequest;
import bookNow.Response.AppointmentResponse;
import bookNow.Security.JwtUserDetails;
import bookNow.Service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAppointment() {
        // Arrange: Erstelle eine Mock-AppointmentCreateRequest und einen Mock-User
        AppointmentCreateRequest request = new AppointmentCreateRequest();
        request.setAppointmentDate(LocalDate.of(2024, 10, 15));
        request.setAppointmentTime(LocalTime.of(14, 30));
        request.setUserId(1L);

        JwtUserDetails userDetails = new JwtUserDetails(1L, "username", "password", null, null);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        AppointmentModel createdAppointment = new AppointmentModel();
        createdAppointment.setAppointmentId(1L);
        when(appointmentService.createAppointment(request)).thenReturn(createdAppointment);

        // Act: Rufe die Methode createAppointment auf
        ResponseEntity<?> response = appointmentController.createAppointment(request, authentication);

        // Assert: Überprüfe, dass die Rückgabe OK ist und das erstellte AppointmentModel zurückgegeben wird
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdAppointment, response.getBody());
    }

    @Test
    void testGetAllAppointments_withUserId() {
        // Arrange: Erstelle ein Mock-AppointmentModel und fülle die nötigen Daten
        AppointmentModel appointment = new AppointmentModel();
        appointment.setAppointmentId(1L);

        UserModel user = new UserModel();
        user.setId(1L);
        appointment.setUser(user);

        ServiceCompanyModel service = new ServiceCompanyModel();
        service.setServiceId(2L);
        service.setName("Test Service");
        appointment.setService(service);

        CompanyModel company = new CompanyModel();
        company.setId(3L);
        company.setCompanyName("Test Company");
        appointment.setCompany(company);

        appointment.setAppointmentDate(LocalDate.of(2024, 10, 15));
        appointment.setAppointmentTime(LocalTime.of(14, 30));

        // Nutze den Konstruktor von AppointmentResponse, um das Mock-AppointmentModel zu übergeben
        when(appointmentService.getAllAppointments(Optional.of(1L), Optional.empty(), Optional.empty()))
                .thenReturn(List.of(new AppointmentResponse(appointment)));

        // Act: Rufe die Methode getAllAppointments auf
        List<AppointmentResponse> result = appointmentController.getAllAppointments(Optional.of(1L), Optional.empty(), Optional.empty());

        // Assert: Überprüfe, ob die Liste der Termine nicht leer ist und die Termin-ID korrekt ist
        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getAppointmentId());
    }


    @Test
    void testFindByAppointmentId_found() {
        // Arrange: Erstelle ein Mock-AppointmentModel
        AppointmentModel appointment = new AppointmentModel();
        appointment.setAppointmentId(1L);
        when(appointmentService.findByAppointmentId(1L)).thenReturn(appointment);

        // Act: Rufe die Methode findByAppointmentId auf
        AppointmentModel result = appointmentController.findByAppointmentId(1L);

        // Assert: Überprüfe, dass das zurückgegebene AppointmentModel nicht null ist und die ID korrekt ist
        assertNotNull(result);
        assertEquals(1L, result.getAppointmentId());
    }

    @Test
    void testUpdateAppointment_found() {
        // Arrange: Erstelle eine Mock-AppointmentUpdateRequest und ein Mock-AppointmentModel
        AppointmentUpdateRequest updateRequest = new AppointmentUpdateRequest();
        updateRequest.setAppointmentDateRequest(LocalDate.of(2024, 10, 16));
        updateRequest.setAppointmentTimeRequest(LocalTime.of(15, 0));

        AppointmentModel updatedAppointment = new AppointmentModel();
        updatedAppointment.setAppointmentDate(LocalDate.of(2024, 10, 16));
        updatedAppointment.setAppointmentTime(LocalTime.of(15, 0));

        when(appointmentService.updateAppointment(1L, updateRequest)).thenReturn(updatedAppointment);

        // Act: Rufe die Methode updateAppointment auf
        AppointmentModel result = appointmentController.updateAppointment(1L, updateRequest);

        // Assert: Überprüfe, dass das zurückgegebene Model aktualisiert wurde
        assertEquals(LocalDate.of(2024, 10, 16), result.getAppointmentDate());
        assertEquals(LocalTime.of(15, 0), result.getAppointmentTime());
    }

    @Test
    void testDeleteAppointment() {
        // Arrange: Mache nichts für das deleteById
        doNothing().when(appointmentService).deleteAppointment(1L);

        // Act: Rufe die Methode deleteAppointment auf
        appointmentController.deleteAppointment(1L);

        // Assert: Verifiziere, dass deleteAppointment einmal aufgerufen wurde
        verify(appointmentService, times(1)).deleteAppointment(1L);
    }
}

