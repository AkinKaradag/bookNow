package bookNow.Service;

import bookNow.Model.Service;
import bookNow.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCompany {

    @Autowired
    private ServiceRepository serviceRepository;

    public Service createService(Service service) {
        return serviceRepository.save(service);
    }

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    // Methoden zum Ändern und Löschen von Services
}
