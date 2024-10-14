package Controller;

import bookNow.Controller.ServiceCompanyController;
import bookNow.Model.CompanyModel;
import bookNow.Model.ServiceCompanyModel;
import bookNow.Requests.ServiceCompanyRequest;
import bookNow.Requests.ServiceCompanyUpdate;
import bookNow.Response.ServiceCompanyResponse;
import bookNow.Security.JwtUserDetails;
import bookNow.Service.CompanyService;
import bookNow.Service.ServiceCompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceCompanyControllerTest {

    // Mocks für Service- und Repository-Objekte
    @Mock
    private ServiceCompanyService serviceCompanyService;

    @Mock
    private CompanyService companyService;

    @Mock
    private Authentication authentication;

    // Controller mit injizierten Mocks
    @InjectMocks
    private ServiceCompanyController serviceCompanyController;

    // Initialisierung der Mocks vor jedem Test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateServiceCompany() {
        // Arrange: Erstellen einer Mock-Anfrage für einen neuen Service
        ServiceCompanyRequest request = new ServiceCompanyRequest();
        request.setName("Test Service");
        request.setDescription("Description of Test Service");
        request.setPrice(BigDecimal.valueOf(99.99));
        request.setDuration(60);

        // Mock der Authentifizierung und Extraktion der Benutzer-Details
        JwtUserDetails userDetails = new JwtUserDetails(1L, "Test Company", "password", null, null);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Mock der Firmensuche und des Service-Objekts
        CompanyModel mockCompany = new CompanyModel();
        when(companyService.findById(1L)).thenReturn(mockCompany);

        ServiceCompanyModel serviceCompany = new ServiceCompanyModel();
        serviceCompany.setName("Test Service");
        when(serviceCompanyService.createServiceCompany(any(ServiceCompanyRequest.class), eq(1L))).thenReturn(serviceCompany);

        // Act: Aufruf der Methode zur Erstellung des Services
        ResponseEntity<ServiceCompanyModel> response = serviceCompanyController.createServiceCompany(request, authentication);

        // Assert: Überprüfung des Ergebnisses
        assertNotNull(response); // Überprüfen, dass die Antwort nicht null ist
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Überprüfen, dass der Status OK ist
        assertEquals("Test Service", response.getBody().getName()); // Überprüfen, dass der Servicename korrekt ist
        verify(serviceCompanyService, times(1)).createServiceCompany(request, 1L); // Sicherstellen, dass die Methode genau einmal aufgerufen wurde
    }

    @Test
    void testGetAllServiceCompanies_withCompanyId() {
        // Arrange: Erstellen eines Mock-Services mit einem zugehörigen Unternehmen
        ServiceCompanyModel service = new ServiceCompanyModel();
        service.setServiceId(1L);
        service.setName("Test Service");
        service.setDescription("Description of Test Service");
        service.setPrice(BigDecimal.valueOf(99.99));
        service.setDuration(60);

        CompanyModel company = new CompanyModel();
        company.setId(2L);
        company.setCompanyName("Test Company");
        service.setCompany(company);

        // Stubbing der Service-Methode zur Rückgabe eines Response-Objekts
        when(serviceCompanyService.getAllServiceCompanies(Optional.of(2L)))
                .thenReturn(List.of(new ServiceCompanyResponse(service)));

        // Act: Aufruf der Methode, um alle Services einer Firma abzurufen
        List<ServiceCompanyResponse> result = serviceCompanyController.getAllServiceCompanies(Optional.of(2L));

        // Assert: Überprüfen der Ergebnisse
        assertNotNull(result); // Sicherstellen, dass das Resultat nicht null ist
        assertFalse(result.isEmpty()); // Sicherstellen, dass die Liste nicht leer ist
        assertEquals(1L, result.get(0).getServiceId()); // Überprüfen der Service-ID
        assertEquals("Test Company", result.get(0).getCompanyName()); // Überprüfen des Firmennamens
        verify(serviceCompanyService, times(1)).getAllServiceCompanies(Optional.of(2L)); // Sicherstellen, dass der Service-Aufruf erfolgt ist
    }

    @Test
    void testFindByServiceId_found() {
        // Arrange: Mocken eines bestehenden Service
        ServiceCompanyModel service = new ServiceCompanyModel();
        service.setServiceId(1L);
        when(serviceCompanyService.findByServiceId(1L)).thenReturn(service);

        // Act: Aufruf der Methode zur Service-Suche nach ID
        ServiceCompanyModel result = serviceCompanyController.findByServiceId(1L);

        // Assert: Überprüfen der Ergebnisse
        assertNotNull(result); // Sicherstellen, dass das Ergebnis nicht null ist
        assertEquals(1L, result.getServiceId()); // Sicherstellen, dass die Service-ID korrekt ist
        verify(serviceCompanyService, times(1)).findByServiceId(1L); // Sicherstellen, dass der Service korrekt aufgerufen wurde
    }

    @Test
    void testUpdateService_found() {
        // Arrange: Erstellen einer Aktualisierungsanfrage und eines aktualisierten Service-Modells
        ServiceCompanyUpdate updateRequest = new ServiceCompanyUpdate();
        updateRequest.setName("Updated Service");
        updateRequest.setDescription("Updated Description");

        ServiceCompanyModel updatedService = new ServiceCompanyModel();
        updatedService.setName("Updated Service");

        when(serviceCompanyService.updateService(1L, updateRequest)).thenReturn(updatedService);

        // Act: Aufruf der Aktualisierungs-Methode
        ServiceCompanyModel result = serviceCompanyController.updateService(1L, updateRequest);

        // Assert: Überprüfen der aktualisierten Service-Daten
        assertNotNull(result); // Sicherstellen, dass das Ergebnis nicht null ist
        assertEquals("Updated Service", result.getName()); // Sicherstellen, dass der Name aktualisiert wurde
        verify(serviceCompanyService, times(1)).updateService(1L, updateRequest); // Sicherstellen, dass die Aktualisierungsmethode aufgerufen wurde
    }

    @Test
    void testDeleteService() {
        // Arrange: Stub für die Löschmethode
        doNothing().when(serviceCompanyService).deleteService(1L);

        // Act: Aufruf der Löschmethode
        serviceCompanyController.deleteUser(1L);

        // Assert: Verifizieren, dass der Löschaufruf erfolgt ist
        verify(serviceCompanyService, times(1)).deleteService(1L); // Sicherstellen, dass die Löschmethode genau einmal aufgerufen wurde
    }
}
