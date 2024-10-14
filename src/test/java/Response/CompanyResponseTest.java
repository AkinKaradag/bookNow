package Response;

import bookNow.Model.CompanyModel;
import bookNow.Response.CompanyResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testklasse für die CompanyResponse.
 */
public class CompanyResponseTest {

    @Test
    public void testCompanyResponseInitialization() {
        // Arrange: Erstellen eines Mock CompanyModel-Objekts mit Beispieldaten
        CompanyModel companyModel = new CompanyModel();
        companyModel.setId(1L);
        companyModel.setCompanyName("Test Company");
        companyModel.setDescription("This is a test company.");
        companyModel.setCompanyAddress("123 Test St.");
        companyModel.setCompanyCity("Test City");
        companyModel.setCompanyPostalCode(12345);
        companyModel.setPhoneNumber("123-456-7890");
        companyModel.setPassword("testPassword");

        // Act: Initialisierung einer CompanyResponse basierend auf dem Mock-Objekt
        CompanyResponse companyResponse = new CompanyResponse(companyModel);

        // Assert: Überprüfen, ob alle Felder korrekt initialisiert wurden
        assertEquals(1L, companyResponse.getCompanyId());
        assertEquals("Test Company", companyResponse.getCompanyName());
        assertEquals("This is a test company.", companyResponse.getCompanyDescription());
        assertEquals("123 Test St.", companyResponse.getCompanyAddress());
        assertEquals("Test City", companyResponse.getCompanyCity());
        assertEquals(12345, companyResponse.getCompanyPLZ());
        assertEquals("123-456-7890", companyResponse.getCompanyPhone());
        assertEquals("testPassword", companyResponse.getPassword());
    }
}

