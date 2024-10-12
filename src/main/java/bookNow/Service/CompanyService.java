package bookNow.Service;

import bookNow.Model.CompanyModel;
import bookNow.Model.UserModel;
import bookNow.Repository.CompanyRepository;
import bookNow.Requests.CompanyUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {


    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
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

    public CompanyModel updateCompany(Long id, CompanyUpdate updatedCompany) {
        Optional<CompanyModel> company = companyRepository.findById(id);
        if (company.isPresent()) {
            CompanyModel foundCompany = company.get();
            foundCompany.setCompanyName(updatedCompany.getCompanyName());
            foundCompany.setCompanyAddress(updatedCompany.getCompanyAddress());
            foundCompany.setCompanyCity(updatedCompany.getCompanyCity());
            foundCompany.setCompanyPostalCode(updatedCompany.getCompanyPostalCode());
            foundCompany.setPhoneNumber(updatedCompany.getPhoneNumber());
            foundCompany.setDescription(updatedCompany.getDescription());
            if (updatedCompany.getPassword() != null && !updatedCompany.getPassword().isEmpty()) {
                String encryptedPassword = passwordEncoder.encode(updatedCompany.getPassword());
                foundCompany.setPassword(encryptedPassword);
            }
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
