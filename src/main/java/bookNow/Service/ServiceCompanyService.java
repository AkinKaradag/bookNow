package bookNow.Service;

import bookNow.Model.ServiceCompanyModel;
import bookNow.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCompanyService {

    @Autowired
    private ServiceRepository serviceRepository;

    public ServiceCompanyModel createServiceCompany(ServiceCompanyModel serviceCompany) {
        return serviceRepository.save(serviceCompany);
    }

    public List<ServiceCompanyModel> getAllServiceCompanies() {
        return serviceRepository.findAll();
    }

    // Weitere Methoden für Update, Delete usw.
}

