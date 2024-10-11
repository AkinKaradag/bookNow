package bookNow.Service;

import bookNow.Model.CompanyModel;
import bookNow.Model.ServiceCompanyModel;
import bookNow.Repository.ServiceRepository;
import bookNow.Requests.ServiceCompanyRequest;
import bookNow.Requests.ServiceCompanyUpdate;
import bookNow.Response.ServiceCompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceCompanyService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CompanyService companyService;

    public ServiceCompanyService(ServiceRepository serviceRepository, CompanyService companyService) {
        this.serviceRepository = serviceRepository;
        this.companyService = companyService;
    }

    public ServiceCompanyModel createServiceCompany(ServiceCompanyRequest newServiceCompany, Long id) {

        CompanyModel company = companyService.findById(id);

        System.out.println("Company für ID " + id + ": " + (company != null ? company.getCompanyName() : "Nicht gefunden"));

        if (company == null) {
            throw new IllegalArgumentException("Company not found");
        }

            ServiceCompanyModel toSave = new ServiceCompanyModel();
            toSave.setServiceId(newServiceCompany.getServiceId());
            toSave.setName(newServiceCompany.getName());
            toSave.setDescription(newServiceCompany.getDescription());
            toSave.setPrice(newServiceCompany.getPrice());
            toSave.setDuration(newServiceCompany.getDuration());
            toSave.setCompany(company);

        System.out.println("Speichern des Services: " + toSave.getName());

            ServiceCompanyModel saved = serviceRepository.save(toSave);

        System.out.println("Service erfolgreich gespeichert mit ID: " + saved.getServiceId());

             return saved;
        
    }

    public List<ServiceCompanyResponse> getAllServiceCompanies(Optional<Long> companyId) {
        List<ServiceCompanyModel> listServices;
        if(companyId.isPresent()) {
            listServices = serviceRepository.findByCompany_Id(companyId.get());
        } else {
            listServices = serviceRepository.findAll();
        }
        return listServices.stream().map(ServiceCompanyResponse::new).collect(Collectors.toList());

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

