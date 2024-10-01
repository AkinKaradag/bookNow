package bookNow.Service;

import bookNow.Model.UserModel;
import bookNow.Repository.UserRepository;
import bookNow.Security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserModel user = userRepository.findByName(username);

        return JwtUserDetails.build(user);
    }

    public UserDetails loadUserById(Long id) {
        UserModel user = userRepository.findById(id).get();

        return JwtUserDetails.build(user);
    }

}
