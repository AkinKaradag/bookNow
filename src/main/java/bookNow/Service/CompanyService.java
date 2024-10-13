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

/**
 * Diese Service-Klasse behandelt die Geschäftslogik für die Verwaltung von Firmen.
 */
@Service
public class CompanyService {


    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Konstruktor für die Injektion von CompanyRepository und PasswordEncoder
    @Autowired
    public CompanyService(CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Methode zum Erstellen einer neuen Firma
    public CompanyModel createCompany(CompanyModel company) {
        return companyRepository.save(company);
    }

    // Methode zum Abrufen aller Firmen
    public List<CompanyModel> getAllCompanies() {
        return companyRepository.findAll();
    }

    // Methode zum Finden einer Firma nach ihrer ID
    public CompanyModel findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    // Methode zum Aktualisieren einer bestehenden Firma
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

    // Methode zum Löschen einer Firma anhand ihrer ID
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    // Methode zum Finden einer Firma nach ihrem Namen
    public CompanyModel findByName(String userName) {
        return companyRepository.findByCompanyName(userName);
    }
}
