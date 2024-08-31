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

    @PostMapping("/create")
    public ServiceCompanyModel createServiceCompany(@RequestBody ServiceCompanyModel serviceCompany) {
        return serviceCompanyService.createServiceCompany(serviceCompany);
    }

    @GetMapping
    public List<ServiceCompanyModel> getAllServiceCompanies() {
        return serviceCompanyService.getAllServiceCompanies();
    }

    // Weitere Endpunkte für Update, Delete usw.
}

