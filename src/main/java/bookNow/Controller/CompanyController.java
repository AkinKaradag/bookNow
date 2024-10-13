package bookNow.Controller;

import bookNow.Model.CompanyModel;
import bookNow.Requests.CompanyUpdate;
import bookNow.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller für die Verwaltung der Firmenentitäten.
 */
@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * Erstellt eine neue Firma.
     * @param company Das Firmenmodell-Objekt mit den Firmeninformationen.
     * @return Das erstellte Firmenmodell.
     */
    @PostMapping
    public CompanyModel createCompany(@RequestBody CompanyModel company) {
        return companyService.createCompany(company);
    }

    /**
     * Ruft alle Firmen ab.
     * @return Eine Liste aller Firmenmodelle.
     */
    @GetMapping
    public List<CompanyModel> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    /**
     * Ruft eine Firma anhand ihrer ID ab.
     * @param id Die ID der Firma.
     * @return Das Firmenmodell der gesuchten Firma.
     */
    @GetMapping("/{id}")
    public CompanyModel findById(@PathVariable Long id) {
        //custom exception
        return companyService.findById(id);
    }

    /**
     * Aktualisiert die Daten einer Firma.
     * @param id Die ID der zu aktualisierenden Firma.
     * @param updatedCompany Die neuen Firmendaten.
     * @return Das aktualisierte Firmenmodell.
     */
    @PutMapping("/{id}")
    public CompanyModel updateCompany(@PathVariable Long id, @RequestBody CompanyUpdate updatedCompany) {
        return companyService.updateCompany(id, updatedCompany);

    }

    /**
     * Löscht eine Firma anhand ihrer ID.
     * @param id Die ID der zu löschenden Firma.
     */
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }

}
