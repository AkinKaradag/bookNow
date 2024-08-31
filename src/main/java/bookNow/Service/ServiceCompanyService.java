package bookNow.Service;

import bookNow.Model.ServiceCompanyModel;
import bookNow.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCompanyService {

    @Autowired
    private ServiceRepository serviceRepository;

    public ServiceCompanyService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public ServiceCompanyModel createServiceCompany(ServiceCompanyModel serviceCompany) {
        return serviceRepository.save(serviceCompany);
    }

    public List<ServiceCompanyModel> getAllServiceCompanies() {
        return serviceRepository.findAll();
    }

    public ServiceCompanyModel getServiceById(Long serviceId) {
        return serviceRepository.findById(serviceId).orElse(null);
    }

    public ServiceCompanyModel updateService(Long serviceId, ServiceCompanyModel updatedService) {
        Optional<ServiceCompanyModel> service = serviceRepository.findById(serviceId);
        if (service.isPresent()) {
            ServiceCompanyModel foundService = service.get();
            foundService.setName(updatedService.getName());
            foundService.setDescription(updatedService.getDescription());
            return serviceRepository.save(foundService);
        } else {
            return null;
        }
    }

    public void deleteService(Long serviceId) {
        serviceRepository.deleteById(serviceId);
    }
    // Weitere Methoden für Update, Delete usw.
}

