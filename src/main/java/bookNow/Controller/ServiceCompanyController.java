package bookNow.Controller;

import bookNow.Model.ServiceCompanyModel;
import bookNow.Service.ServiceCompanyService;
import bookNow.Requests.ServiceCompanyRequest;
import bookNow.Requests.ServiceCompanyUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/service-companies")
public class ServiceCompanyController {

    @Autowired
    private ServiceCompanyService serviceCompanyService;

    @PostMapping
    public ServiceCompanyModel createServiceCompany(@RequestBody ServiceCompanyRequest serviceCompany) {
        return serviceCompanyService.createServiceCompany(serviceCompany);
    }

    @GetMapping
    public List<ServiceCompanyModel> getAllServiceCompanies(@RequestParam Optional<Long> companyId) {
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

