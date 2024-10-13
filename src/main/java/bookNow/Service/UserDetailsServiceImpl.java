package bookNow.Service;

import bookNow.Model.CompanyModel;
import bookNow.Model.UserModel;
import bookNow.Repository.CompanyRepository;
import bookNow.Repository.UserRepository;
import bookNow.Security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Diese Service-Klasse ist für die Authentifizierung von Benutzern und Firmen zuständig.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private CompanyRepository companyRepository;

    // Konstruktor zur Injektion von UserRepository und CompanyRepository
    public UserDetailsServiceImpl(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    // Lädt einen Benutzer basierend auf dem Benutzernamen (Name oder Firmenname)
    @Override
    public UserDetails loadUserByUsername(String username) {
        // Prüfen, ob es sich um einen PrivateUser handelt
        UserModel user = userRepository.findByName(username);
        if (user != null) {
            return JwtUserDetails.build(user);
        }

        // Falls kein normaler User gefunden wird, prüfen, ob es ein CompanyUser ist
        CompanyModel company = companyRepository.findByCompanyName(username);
        if (company != null) {
            return JwtUserDetails.build(company);
        }

        throw new UsernameNotFoundException("User or Company not found with name/companyName: " + username);
    }

    // Lädt einen Benutzer basierend auf seiner ID
    public UserDetails loadUserById(Long id) {
        UserModel user = userRepository.findById(id).get();

        return JwtUserDetails.build(user);
    }

    // Lädt eine Firma basierend auf ihrer ID
    public UserDetails loadCompanyById(Long id) {
        // Versucht, die Company anhand der ID zu finden
        Optional<CompanyModel> companyOptional = companyRepository.findById(id);

        // Überprüft, ob ein Wert vorhanden ist
        if (companyOptional.isPresent()) {
            return JwtUserDetails.build(companyOptional.get());
        } else {
            throw new NoSuchElementException("Company with ID " + id + " not found");
        }
    }


}
