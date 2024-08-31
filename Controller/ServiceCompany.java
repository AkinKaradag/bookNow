package Controller;

import bookNow.Model.Service;
import bookNow.Service.ServiceCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceCompany {

    @Autowired
    private ServiceCompany serviceCompany;

    @PostMapping("/create")
    public Service createService(@RequestBody Service service) {
        return serviceCompany.createService(service);
    }

    @GetMapping
    public List<Service> getAllServices() {
        return serviceCompany.getAllServices();
    }

    // Weitere Endpunkte zum Ändern und Löschen
}
