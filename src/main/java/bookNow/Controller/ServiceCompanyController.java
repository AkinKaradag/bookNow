package bookNow.Controller;

import bookNow.Model.CompanyModel;
import bookNow.Model.ServiceCompanyModel;
import bookNow.Response.ServiceCompanyResponse;
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
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<ServiceCompanyModel> createServiceCompany(@RequestBody ServiceCompanyRequest serviceCompanyRequest, Authentication authentication) {
        CompanyModel company = companyService.findByName(authentication.getName());

        if (company == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ServiceCompanyModel serviceCompany = serviceCompanyService.createServiceCompany(serviceCompanyRequest, company);
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

