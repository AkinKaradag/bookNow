package Services;

import bookNow.Model.CompanyModel;
import bookNow.Repository.CompanyRepository;
import bookNow.Requests.CompanyUpdate;
import bookNow.Service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialisiere passwordEncoder als BCryptPasswordEncoder
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void testCreateCompany() {
        CompanyModel company = new CompanyModel();
        company.setCompanyName("Test Company");
        company.setPassword("plainPassword");

        // Tatsächliche Kodierung des Passworts
        String encodedPassword = passwordEncoder.encode(company.getPassword());

        // Speichern des Unternehmens mit dem kodierten Passwort
        when(companyRepository.save(any(CompanyModel.class))).thenAnswer(invocation -> {
            CompanyModel savedCompany = invocation.getArgument(0);
            savedCompany.setPassword(encodedPassword);
            return savedCompany;
        });

        CompanyModel result = companyService.createCompany(company);

        assertNotNull(result);
        assertEquals("Test Company", result.getCompanyName());
        assertTrue(passwordEncoder.matches("plainPassword", result.getPassword()),
                "Das verschlüsselte Passwort sollte mit dem Original übereinstimmen.");
    }



    @Test
    void testGetAllCompanies() {
        // Testet das Abrufen aller Firmen
        CompanyModel company1 = new CompanyModel();
        company1.setCompanyName("Company 1");

        CompanyModel company2 = new CompanyModel();
        company2.setCompanyName("Company 2");

        when(companyRepository.findAll()).thenReturn(List.of(company1, company2));

        List<CompanyModel> result = companyService.getAllCompanies();

        assertEquals(2, result.size());
        assertEquals("Company 1", result.get(0).getCompanyName());
        assertEquals("Company 2", result.get(1).getCompanyName());
    }

    @Test
    void testFindById_found() {
        // Testet das Finden einer Firma mit vorhandener ID
        CompanyModel company = new CompanyModel();
        company.setId(1L);
        company.setCompanyName("Test Company");

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        CompanyModel result = companyService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindById_notFound() {
        // Testet das Finden einer Firma mit einer nicht vorhandenen ID
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        CompanyModel result = companyService.findById(1L);

        assertNull(result);
    }

    @Test
    void testUpdateCompany_found() {
        // Testet das erfolgreiche Aktualisieren einer Firma
        CompanyModel existingCompany = new CompanyModel();
        existingCompany.setId(1L);
        existingCompany.setCompanyName("Old Name");

        CompanyUpdate update = new CompanyUpdate();
        update.setCompanyName("New Name");

        when(companyRepository.findById(1L)).thenReturn(Optional.of(existingCompany));
        when(companyRepository.save(any(CompanyModel.class))).thenReturn(existingCompany);

        CompanyModel result = companyService.updateCompany(1L, update);

        assertNotNull(result);
        assertEquals("New Name", result.getCompanyName());
    }

    @Test
    void testUpdateCompany_notFound() {
        // Testet das Aktualisieren einer Firma, die nicht existiert
        CompanyUpdate update = new CompanyUpdate();
        update.setCompanyName("New Name");

        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        CompanyModel result = companyService.updateCompany(1L, update);

        assertNull(result);
    }

    @Test
    void testDeleteCompany() {
        // Testet das Löschen einer Firma anhand ihrer ID
        doNothing().when(companyRepository).deleteById(1L);

        companyService.deleteCompany(1L);

        verify(companyRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByName() {
        // Testet das Finden einer Firma anhand ihres Namens
        CompanyModel company = new CompanyModel();
        company.setCompanyName("Test Company");

        when(companyRepository.findByCompanyName("Test Company")).thenReturn(company);

        CompanyModel result = companyService.findByName("Test Company");

        assertNotNull(result);
        assertEquals("Test Company", result.getCompanyName());
    }
}

