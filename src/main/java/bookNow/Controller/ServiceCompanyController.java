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

@RestController
@RequestMapping("/service-companies")
public class ServiceCompanyController {

    @Autowired
    private ServiceCompanyService serviceCompanyService;
    @Autowired
    private CompanyService companyService;



    @PostMapping
    public ResponseEntity<ServiceCompanyModel> createServiceCompany(@RequestBody ServiceCompanyRequest serviceCompanyRequest, Authentication authentication) {
        System.out.println("Benutzerrollen: " + authentication.getAuthorities());
        System.out.println("POST /service-companies aufgerufen");

        Long id;
        try {
            if(authentication.getPrincipal() instanceof JwtUserDetails) {
                JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
                id = userDetails.getId();
            } else {
                throw new IllegalArgumentException("Principal ist kein JwtUserDetails-Objekt");
            }
            System.out.println("Empfangene Company ID im Controller: " + id);
        } catch (NumberFormatException e) {
            System.out.println("Fehler beim Parsen der Company ID: " + authentication.getPrincipal());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        CompanyModel company = companyService.findById(id);

        if (company == null) {
            System.out.println("Company mit ID " + id + " nicht gefunden.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ServiceCompanyModel serviceCompany = serviceCompanyService.createServiceCompany(serviceCompanyRequest, id);

if (serviceCompany == null) {
    System.out.println("ServiceCompany ist null");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
}
System.out.println("ServiceCompany erfolgreich erstellt: " + serviceCompany);
return ResponseEntity.ok(serviceCompany);
    }

    @GetMapping
    public List<ServiceCompanyResponse> getAllServiceCompanies(@RequestParam Optional<Long> companyId) {
            return serviceCompanyService.getAllServiceCompanies(companyId);
        }


    @GetMapping("/{serviceId}")
    public ServiceCompanyModel findByServiceId(@PathVariable Long serviceId) {
        //custom exception
        return serviceCompanyService.findByServiceId(serviceId);
    }

    @PutMapping("/{serviceId}")
    public ServiceCompanyModel updateService(@PathVariable Long serviceId, @RequestBody ServiceCompanyUpdate updatedService) {
        return serviceCompanyService.updateService(serviceId, updatedService);

    }

    @DeleteMapping("/{serviceId}")
    public void deleteUser(@PathVariable Long serviceId) {
        serviceCompanyService.deleteService(serviceId);
    }

    // Weitere Endpunkte für Update, Delete usw.
}

