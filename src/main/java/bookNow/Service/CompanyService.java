package bookNow.Service;

import bookNow.Model.CompanyModel;
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

    public CompanyModel findByCompanyId(Long companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public CompanyModel updateCompany(Long companyId, CompanyModel updatedCompany) {
        Optional<CompanyModel> company = companyRepository.findById(companyId);
        if (company.isPresent()) {
            CompanyModel foundCompany = company.get();
            foundCompany.setCompanyName(updatedCompany.getCompanyName());
            foundCompany.setCompanyAddress(updatedCompany.getCompanyAddress());
            foundCompany.setCompanyCity(updatedCompany.getCompanyCity());
            foundCompany.setPhoneNumber(updatedCompany.getPhoneNumber());
            foundCompany.setPhoneNumber(updatedCompany.getPhoneNumber());
            foundCompany.setDescription(updatedCompany.getDescription());
            foundCompany.setUserType(updatedCompany.getUserType());
            return companyRepository.save(foundCompany);
        } else {
            return null;
        }
    }

    public void deleteCompany(Long companyId) {
        companyRepository.deleteById(companyId);
    }

}
