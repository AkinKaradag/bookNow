package bookNow.Service;

import bookNow.Model.CompanyModel;
import bookNow.Model.UserModel;
import bookNow.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {


    @Autowired
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyModel createCompany(CompanyModel company) {
        return companyRepository.save(company);
    }

    public List<CompanyModel> getAllCompanies() {
        return companyRepository.findAll();
    }

    public CompanyModel findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public CompanyModel updateCompany(Long id, CompanyModel updatedCompany) {
        Optional<CompanyModel> company = companyRepository.findById(id);
        if (company.isPresent()) {
            CompanyModel foundCompany = company.get();
            foundCompany.setCompanyName(updatedCompany.getCompanyName());
            foundCompany.setCompanyAddress(updatedCompany.getCompanyAddress());
            foundCompany.setCompanyCity(updatedCompany.getCompanyCity());
            foundCompany.setCompanyPostalCode(updatedCompany.getCompanyPostalCode());
            foundCompany.setPhoneNumber(updatedCompany.getPhoneNumber());
            foundCompany.setDescription(updatedCompany.getDescription());
            foundCompany.setUserType(updatedCompany.getUserType());
            return companyRepository.save(foundCompany);
        } else {
            return null;
        }
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    public CompanyModel findByName(String userName) {
        return companyRepository.findByCompanyName(userName);
    }
}
