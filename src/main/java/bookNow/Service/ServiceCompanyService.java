package bookNow.Service;

import bookNow.Model.CompanyModel;
import bookNow.Model.ServiceCompanyModel;
import bookNow.Repository.ServiceRepository;
import bookNow.requests.ServiceCompanyRequest;
import bookNow.requests.ServiceCompanyUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCompanyService {

    @Autowired
    private ServiceRepository serviceRepository;

    private CompanyService companyService;

    public ServiceCompanyService(ServiceRepository serviceRepository, CompanyService companyService) {
        this.serviceRepository = serviceRepository;
        this.companyService = companyService;
    }

    public ServiceCompanyModel createServiceCompany(ServiceCompanyRequest newServiceCompany) {
        CompanyModel company = companyService.findByCompanyId(newServiceCompany.getCompanyId());
        if (company == null) {
            return null;
        } else {
            ServiceCompanyModel toSave = new ServiceCompanyModel();
            toSave.setName(newServiceCompany.getName());
            toSave.setDescription(newServiceCompany.getDescription());
            toSave.setPrice(newServiceCompany.getPrice());
            toSave.setDuration(newServiceCompany.getDuration());
            toSave.setCompany(company);
            return serviceRepository.save(toSave);
        }
    }

    public List<ServiceCompanyModel> getAllServiceCompanies(Optional<Long> companyId) {
        if(companyId.isPresent()) {
            return serviceRepository.findByCompanyId(companyId.get());
        } else {
        return serviceRepository.findAll();
        }
    }

    public ServiceCompanyModel findByServiceId(Long serviceId) {
        return serviceRepository.findById(serviceId).orElse(null);
    }

    public ServiceCompanyModel updateService(Long serviceId, ServiceCompanyUpdate updatedService) {
        Optional<ServiceCompanyModel> service = serviceRepository.findById(serviceId);
        if (service.isPresent()) {
            ServiceCompanyModel toUpdate = service.get();
            toUpdate.setName(updatedService.getName());
            toUpdate.setDescription(updatedService.getDescription());
            toUpdate.setPrice(updatedService.getPrice());
            toUpdate.setDuration(updatedService.getDuration());
            return serviceRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    public void deleteService(Long serviceId) {
        serviceRepository.deleteById(serviceId);
    }
    // Weitere Methoden für Update, Delete usw.
}

