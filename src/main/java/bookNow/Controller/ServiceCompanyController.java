package bookNow.Controller;

import bookNow.Model.CompanyModel;
import bookNow.Model.ServiceCompanyModel;
import bookNow.Response.ServiceCompanyResponse;
import bookNow.Security.JwtUserDetails;
import bookNow.Service.CompanyService;
import bookNow.Service.ServiceCompanyService;
import bookNow.Requests.ServiceCompanyRequest;
import bookNow.Requests.ServiceCompanyUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller für die Verwaltung der Service-Firmen-Entitäten.
 */
@RestController
@RequestMapping("/service-companies")
public class ServiceCompanyController {

    @Autowired
    private ServiceCompanyService serviceCompanyService;
    @Autowired
    private CompanyService companyService;

    /**
     * Erstellt einen neuen Service für eine Firma.
     * @param serviceCompanyRequest Die Details des zu erstellenden Services.
     * @param authentication Die Authentifizierung des aktuellen Benutzers.
     * @return Das erstellte Service-Objekt oder ein Fehlercode.
     */
    @PostMapping
    public ResponseEntity<ServiceCompanyModel> createServiceCompany(@RequestBody ServiceCompanyRequest serviceCompanyRequest, Authentication authentication) {

        Long id;
        try {
            if(authentication.getPrincipal() instanceof JwtUserDetails) {
                JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
                id = userDetails.getId();
            } else {
                throw new IllegalArgumentException("Principal ist kein JwtUserDetails-Objekt");
            }} catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        CompanyModel company = companyService.findById(id);
        if (company == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ServiceCompanyModel serviceCompany = serviceCompanyService.createServiceCompany(serviceCompanyRequest, id);

if (serviceCompany == null) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
}
return ResponseEntity.ok(serviceCompany);
    }

    /**
     * Ruft alle Services ab, optional gefiltert nach Firmen-ID.
     * @param companyId Die optionale ID der Firma.
     * @return Eine Liste aller passenden Services.
     */
    @GetMapping
    public List<ServiceCompanyResponse> getAllServiceCompanies(@RequestParam Optional<Long> companyId) {
            return serviceCompanyService.getAllServiceCompanies(companyId);
        }

    /**
     * Ruft einen Service anhand seiner ID ab.
     * @param serviceId Die ID des Services.
     * @return Das Servicemodell des gesuchten Services.
     */
    @GetMapping("/{serviceId}")
    public ServiceCompanyModel findByServiceId(@PathVariable Long serviceId) {
        //custom exception
        return serviceCompanyService.findByServiceId(serviceId);
    }

    /**
     * Aktualisiert die Daten eines Services.
     * @param serviceId Die ID des zu aktualisierenden Services.
     * @param updatedService Die neuen Servicedaten.
     * @return Das aktualisierte Servicemodell.
     */
    @PutMapping("/{serviceId}")
    public ServiceCompanyModel updateService(@PathVariable Long serviceId, @RequestBody ServiceCompanyUpdate updatedService) {
        return serviceCompanyService.updateService(serviceId, updatedService);

    }

    /**
     * Löscht einen Service anhand seiner ID.
     * @param serviceId Die ID des zu löschenden Services.
     */
    @DeleteMapping("/{serviceId}")
    public void deleteUser(@PathVariable Long serviceId) {
        serviceCompanyService.deleteService(serviceId);
    }

}

