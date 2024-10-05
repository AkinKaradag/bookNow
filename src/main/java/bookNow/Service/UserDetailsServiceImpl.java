package bookNow.Service;

import bookNow.Model.CompanyModel;
import bookNow.Model.UserModel;
import bookNow.Repository.CompanyRepository;
import bookNow.Repository.UserRepository;
import bookNow.Security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private CompanyRepository companyRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserModel user = userRepository.findByName(username);

        return JwtUserDetails.build(user);
    }

    public UserDetails loadCompanyByCompanyName(String companyName) {
        CompanyModel company = companyRepository.findByCompanyName(companyName);

        return JwtUserDetails.build(company);
    }

    public UserDetails loadUserById(Long id) {
        UserModel user = userRepository.findById(id).get();

        return JwtUserDetails.build(user);
    }

    public UserDetails loadCompanyById(Long id) {
        CompanyModel company = companyRepository.findById(id).get();

        return JwtUserDetails.build(company);
    }

}
