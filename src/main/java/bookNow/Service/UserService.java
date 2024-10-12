package bookNow.Service;

import bookNow.Model.UserModel;
import bookNow.Repository.UserRepository;
import bookNow.Requests.UserUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel findByUserId(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public UserModel updateUser(Long userID, UserUpdate updatedUser) {
        Optional<UserModel> user = userRepository.findById(userID);
        if (user.isPresent()) {
            UserModel foundUser = user.get();
            foundUser.setName(updatedUser.getUserName());
            foundUser.setEmail(updatedUser.getEmail());
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                String encryptedPassword = passwordEncoder.encode(updatedUser.getPassword());
                foundUser.setPassword(encryptedPassword);
            }
            return userRepository.save(foundUser);
        } else {
            return null;
        }
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserModel findByName(String userName) {
        return userRepository.findByName(userName);
    }

    public UserModel findByEmail(String email) { return userRepository.findByEmail(email);
    }


    // Weitere Methoden für Update, Delete usw.
}


