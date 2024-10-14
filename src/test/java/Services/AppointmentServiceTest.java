package Services;

import bookNow.Model.AppointmentModel;
import bookNow.Model.CompanyModel;
import bookNow.Model.ServiceCompanyModel;
import bookNow.Model.UserModel;
import bookNow.Repository.AppointmentRepository;
import bookNow.Requests.AppointmentCreateRequest;
import bookNow.Requests.AppointmentUpdateRequest;
import bookNow.Response.AppointmentResponse;
import bookNow.Service.AppointmentService;
import bookNow.Service.CompanyService;
import bookNow.Service.ServiceCompanyService;
import bookNow.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private UserService userService;

    @Mock
    private CompanyService companyService;

    @Mock
    private ServiceCompanyService serviceCompanyService;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAppointment() {
        // Arrange: Erstelle einen neuen Termin mit mock-abhängigen Objekten
        //Stellt sicher, dass ein Termin erfolgreich erstellt wird, wenn alle Abhängigkeiten korrekt vorhanden sind
        AppointmentCreateRequest request = new AppointmentCreateRequest();
        request.setAppointmentId(1L);
        request.setAppointmentDate(LocalDate.of(2024, 10, 15));
        request.setAppointmentTime(LocalTime.of(14, 30));
        request.setUserId(1L);
        request.setCompanyId(2L);
        request.setServiceId(3L);

        UserModel mockUser = new UserModel();
        CompanyModel mockCompany = new CompanyModel();
        ServiceCompanyModel mockService = new ServiceCompanyModel();

        when(userService.findByUserId(1L)).thenReturn(mockUser);
        when(companyService.findById(2L)).thenReturn(mockCompany);
        when(serviceCompanyService.findByServiceId(3L)).thenReturn(mockService);

        AppointmentModel savedAppointment = new AppointmentModel();
        savedAppointment.setAppointmentId(1L);

        when(appointmentRepository.save(any(AppointmentModel.class))).thenReturn(savedAppointment);

        // Act: Rufe die Methode auf
        AppointmentModel result = appointmentService.createAppointment(request);

        // Assert: Überprüfe, dass die Methode den erwarteten Termin zurückgibt
        assertNotNull(result);
        assertEquals(1L, result.getAppointmentId());
    }

    @Test
    void testGetAllAppointments_withUserId() {
        // Arrange: Setze ein Mock-Response für Termine
        //Stellt sicher, dass die Methode alle Termine eines Benutzers zurückgibt, wenn die Benutzer-ID angegeben ist
        AppointmentModel appointment = new AppointmentModel();
        appointment.setAppointmentId(1L);

        //Setzt einen Mock-User im Termin, damit der 'userID'-Abruf erfolgreich ist
        UserModel user = new UserModel();
        user.setId(1L);
        appointment.setUser(user);

        //Setzt einen Mock-Service im Termin, damit der 'serviceId'-Abruf erfolgreich ist
        ServiceCompanyModel service = new ServiceCompanyModel();
        service.setServiceId(2L);
        appointment.setService(service);

        //Setzt ein Mock-Unternehmen im Termin, damit der 'companyId'-Abruf erfolgreich ist
        CompanyModel company = new CompanyModel();
        company.setId(3L);
        appointment.setCompany(company);

        when(appointmentRepository.findByUser_Id(1L)).thenReturn(List.of(appointment));

        // Act: Rufe die Methode auf
        List<AppointmentResponse> result = appointmentService.getAllAppointments(Optional.of(1L), Optional.empty(), Optional.empty());

        // Assert: Überprüfe, ob die Liste der Termine zurückgegeben wird
        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getAppointmentId());
    }

    @Test
    void testFindByAppointmentId_found() {
        // Arrange: Erstelle ein Mock-AppointmentModel
        //Stellt sicher, dass die Methode ein vorhandenes Terminobjekt zurückgibt
        AppointmentModel mockAppointment = new AppointmentModel();
        mockAppointment.setAppointmentId(1L);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(mockAppointment));

        // Act: Rufe die Methode auf
        AppointmentModel result = appointmentService.findByAppointmentId(1L);

        // Assert: Überprüfe, dass das richtige AppointmentModel zurückgegeben wird
        assertNotNull(result);
        assertEquals(1L, result.getAppointmentId());
    }

    @Test
    void testUpdateAppointment_found() {
        // Arrange: Erstelle ein Mock-Appointment und eine Aktualisierungsanfrage
        //Stellt sicher, dass die Methode ein vorhandenes Terminobjekt aktualisiert
        AppointmentModel appointment = new AppointmentModel();
        appointment.setAppointmentId(1L);

        AppointmentUpdateRequest updateRequest = new AppointmentUpdateRequest();
        updateRequest.setAppointmentDateRequest(LocalDate.of(2024, 10, 16));
        updateRequest.setAppointmentTimeRequest(LocalTime.of(15, 0));

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(AppointmentModel.class))).thenReturn(appointment);

        // Act: Rufe die Methode auf
        AppointmentModel result = appointmentService.updateAppointment(1L, updateRequest);

        // Assert: Überprüfe, ob die Aktualisierung erfolgreich war
        assertNotNull(result);
        assertEquals(LocalDate.of(2024, 10, 16), result.getAppointmentDate());
        assertEquals(LocalTime.of(15, 0), result.getAppointmentTime());
    }

    @Test
    void testDeleteAppointment() {
        //Stellt sicher, dass die Methode ein vorhandenes Terminobjekt löscht
        // Arrange: Setze das Verhalten für appointmentRepository.deleteById()
        doNothing().when(appointmentRepository).deleteById(1L);

        // Act: Rufe die Methode auf
        appointmentService.deleteAppointment(1L);

        // Assert: Verifiziere, dass deleteById aufgerufen wurde
        verify(appointmentRepository, times(1)).deleteById(1L);
    }
}
