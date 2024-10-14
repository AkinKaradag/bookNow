package Services;

import bookNow.Model.CompanyModel;
import bookNow.Model.ServiceCompanyModel;
import bookNow.Repository.ServiceRepository;
import bookNow.Requests.ServiceCompanyRequest;
import bookNow.Requests.ServiceCompanyUpdate;
import bookNow.Response.ServiceCompanyResponse;
import bookNow.Service.ServiceCompanyService;
import bookNow.Service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceCompanyServiceTest {

    // Mock-Objekte für die Abhängigkeiten von ServiceCompanyService
    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private CompanyService companyService;

    // Die Klasse unter Test, wobei die Mock-Objekte automatisch injiziert werden
    @InjectMocks
    private ServiceCompanyService serviceCompanyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateServiceCompany() {
        // Arrange: Bereite die Testdaten und Mock-Rückgabewerte vor
        //Prüft, ob ein neuer Service erfolgreich erstellt wird, wenn die Firma vorhanden ist
        ServiceCompanyRequest request = new ServiceCompanyRequest();
        request.setServiceId(1L);
        request.setName("Test Service");
        request.setDescription("Description of Test Service");
        request.setPrice(BigDecimal.valueOf(99.99));
        request.setDuration(60);

        CompanyModel mockCompany = new CompanyModel();
        mockCompany.setId(2L);

        when(companyService.findById(2L)).thenReturn(mockCompany);
        when(serviceRepository.save(any(ServiceCompanyModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act: Führe die Methode aus
        ServiceCompanyModel result = serviceCompanyService.createServiceCompany(request, 2L);

        // Assert: Überprüfe, ob das Ergebnis den Erwartungen entspricht
        assertNotNull(result);
        assertEquals("Test Service", result.getName());
        assertEquals("Description of Test Service", result.getDescription());
        verify(serviceRepository, times(1)).save(any(ServiceCompanyModel.class));
    }

    @Test
    void testGetAllServiceCompanies_withCompanyId() {
        // Arrange: Erstelle ein Mock-Service-Objekt und bereite das Repository vor
        //Prüft, ob alle Dienstleistungen einer Firma abgerufen werden können
        ServiceCompanyModel service = new ServiceCompanyModel();
        service.setServiceId(1L);
        service.setName("Test Service");

        CompanyModel company = new CompanyModel();
        company.setId(2L);
        service.setCompany(company);

        when(serviceRepository.findByCompany_Id(2L)).thenReturn(List.of(service));

        // Act: Rufe die Methode mit einem bestimmten companyId auf
        List<ServiceCompanyResponse> result = serviceCompanyService.getAllServiceCompanies(Optional.of(2L));

        // Assert: Überprüfe, ob das Ergebnis korrekt ist und die Daten wie erwartet gefiltert wurden
        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getServiceId());
        verify(serviceRepository, times(1)).findByCompany_Id(2L);
    }

    @Test
    void testFindByServiceId_found() {
        // Arrange: Erstelle ein Mock-Service-Objekt und setze das erwartete Verhalten im Repository
        //Prüft, ob ein vorhandenes Service-Objekt anhand seiner ID gefunden wird
        ServiceCompanyModel service = new ServiceCompanyModel();
        service.setServiceId(1L);

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

        // Act: Führe die Methode aus, um das Service-Objekt nach seiner ID zu finden
        ServiceCompanyModel result = serviceCompanyService.findByServiceId(1L);

        // Assert: Überprüfe, ob das korrekte Service-Objekt zurückgegeben wurde
        assertNotNull(result);
        assertEquals(1L, result.getServiceId());
        verify(serviceRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateService_found() {
        // Arrange: Bereite die zu aktualisierenden Daten und Mock-Objekte vor
        //Prüft, ob ein vorhandenes Service-Objekt erfolgreich aktualisiert wird
        ServiceCompanyModel existingService = new ServiceCompanyModel();
        existingService.setServiceId(1L);

        ServiceCompanyUpdate updateRequest = new ServiceCompanyUpdate();
        updateRequest.setName("Updated Service");
        updateRequest.setDescription("Updated Description");
        updateRequest.setPrice(BigDecimal.valueOf(149.99));
        updateRequest.setDuration(90);

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(existingService));
        when(serviceRepository.save(any(ServiceCompanyModel.class))).thenReturn(existingService);

        // Act: Führe die Aktualisierungsmethode aus
        ServiceCompanyModel result = serviceCompanyService.updateService(1L, updateRequest);

        // Assert: Überprüfe, ob die Aktualisierung erfolgreich war
        assertNotNull(result);
        assertEquals("Updated Service", result.getName());
        verify(serviceRepository, times(1)).save(existingService);
    }

    @Test
    void testDeleteService() {
        // Arrange: Lege fest, dass deleteById keine Ausnahme auslöst
        //Prüft, ob ein vorhandenes Service-Objekt erfolgreich gelöscht wird
        doNothing().when(serviceRepository).deleteById(1L);

        // Act: Führe die Methode zum Löschen des Services aus
        serviceCompanyService.deleteService(1L);

        // Assert: Überprüfe, ob deleteById im Repository aufgerufen wurde
        verify(serviceRepository, times(1)).deleteById(1L);
    }
}

