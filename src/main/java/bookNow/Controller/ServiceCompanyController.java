package bookNow.Controller;

import bookNow.Model.ServiceCompanyModel;
import bookNow.Service.ServiceCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-companies")
public class ServiceCompanyController {

    @Autowired
    private ServiceCompanyService serviceCompanyService;

    @PostMapping
    public ServiceCompanyModel createServiceCompany(@RequestBody ServiceCompanyModel serviceCompany) {
        return serviceCompanyService.createServiceCompany(serviceCompany);
    }

    @GetMapping
    public List<ServiceCompanyModel> getAllServiceCompanies() {
        return serviceCompanyService.getAllServiceCompanies();
    }

    @GetMapping("/{serviceId}")
    public ServiceCompanyModel getServiceById(@PathVariable Long serviceId) {
        //custom exception
        return serviceCompanyService.getServiceById(serviceId);
    }

    @PutMapping("/{serviceId}")
    public ServiceCompanyModel updateService(@PathVariable Long serviceId, @RequestBody ServiceCompanyModel updatedService) {
        return serviceCompanyService.updateService(serviceId, updatedService);

    }

    @DeleteMapping("/{serviceId}")
    public void deleteUser(@PathVariable Long serviceId) {
        serviceCompanyService.deleteService(serviceId);
    }

    // Weitere Endpunkte für Update, Delete usw.
}

