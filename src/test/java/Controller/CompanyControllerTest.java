package Controller;

import bookNow.Controller.CompanyController;
import bookNow.Model.CompanyModel;
import bookNow.Requests.CompanyUpdate;
import bookNow.Service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCompany() {
        // Arrange: Erstelle ein Mock-CompanyModel
        CompanyModel company = new CompanyModel();
        company.setCompanyName("Test Company");

        when(companyService.createCompany(any(CompanyModel.class))).thenReturn(company);

        // Act: Rufe die createCompany-Methode auf
        CompanyModel result = companyController.createCompany(company);

        // Assert: Überprüfe, dass das Ergebnis nicht null ist und der Name stimmt
        assertNotNull(result);
        assertEquals("Test Company", result.getCompanyName());
        verify(companyService, times(1)).createCompany(company);
    }

    @Test
    void testGetAllCompanies() {
        // Arrange: Setze ein Mock-Response für Firmen
        List<CompanyModel> companies = new ArrayList<>();
        companies.add(new CompanyModel());

        when(companyService.getAllCompanies()).thenReturn(companies);

        // Act: Rufe die getAllCompanies-Methode auf
        List<CompanyModel> result = companyController.getAllCompanies();

        // Assert: Überprüfe, ob die Liste nicht leer ist
        assertFalse(result.isEmpty());
        verify(companyService, times(1)).getAllCompanies();
    }

    @Test
    void testFindById_found() {
        // Arrange: Setze ein Mock-Response für eine Firma
        CompanyModel company = new CompanyModel();
        company.setId(1L);

        when(companyService.findById(1L)).thenReturn(company);

        // Act: Rufe die findById-Methode auf
        CompanyModel result = companyController.findById(1L);

        // Assert: Überprüfe, ob das Ergebnis nicht null ist und die ID stimmt
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(companyService, times(1)).findById(1L);
    }

    @Test
    void testUpdateCompany_found() {
        // Arrange: Setze ein Mock-CompanyUpdate und ein Mock-CompanyModel
        CompanyUpdate updateRequest = new CompanyUpdate();
        updateRequest.setCompanyName("Updated Company");

        CompanyModel company = new CompanyModel();
        company.setCompanyName("Updated Company");

        when(companyService.updateCompany(1L, updateRequest)).thenReturn(company);

        // Act: Rufe die updateCompany-Methode auf
        CompanyModel result = companyController.updateCompany(1L, updateRequest);

        // Assert: Überprüfe, dass das Ergebnis nicht null ist und der Name stimmt
        assertNotNull(result);
        assertEquals("Updated Company", result.getCompanyName());
        verify(companyService, times(1)).updateCompany(1L, updateRequest);
    }

    @Test
    void testDeleteCompany() {
        // Arrange: Definiere ein Verhalten für companyService.deleteCompany()
        doNothing().when(companyService).deleteCompany(1L);

        // Act: Rufe die deleteCompany-Methode auf
        companyController.deleteCompany(1L);

        // Assert: Verifiziere, dass deleteCompany mit der korrekten ID aufgerufen wurde
        verify(companyService, times(1)).deleteCompany(1L);
    }
}

