package bookNow.Service;

import bookNow.Model.UserModel;
import bookNow.Repository.UserRepository;
import bookNow.Requests.UserUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Diese Service-Klasse enthält die Geschäftslogik für die Verwaltung von Benutzern.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Konstruktor zur Injektion von UserRepository und PasswordEncoder
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Methode zum Erstellen eines neuen Benutzers
    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }

    // Methode zum Abrufen aller Benutzer
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    // Methode zum Finden eines Benutzers basierend auf seiner ID
    public UserModel findByUserId(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // Methode zum Aktualisieren eines Benutzers
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

    // Methode zum Löschen eines Benutzers basierend auf seiner ID
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    // Methode zum Suchen eines Benutzers nach seinem Namen
    public UserModel findByName(String userName) {
        return userRepository.findByName(userName);
    }

    // Methode zum Suchen eines Benutzers nach seiner Email-Adresse
    public UserModel findByEmail(String email) { return userRepository.findByEmail(email);
    }


    // Weitere Methoden für Update, Delete usw.
}


