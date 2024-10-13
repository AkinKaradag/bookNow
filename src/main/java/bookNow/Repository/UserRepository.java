package bookNow.Repository;

import bookNow.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Dieses Repository-Interface ermöglicht den Zugriff auf Benutzerdaten und bietet CRUD-Operationen.
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    /**
     * Findet einen Benutzer basierend auf dem Benutzernamen.
     * @param userName der Name des Benutzers
     * @return Das UserModel-Objekt des Benutzers
     */
    UserModel findByName(String userName);

    /**
     * Findet einen Benutzer basierend auf der E-Mail-Adresse.
     * @param email die E-Mail-Adresse des Benutzers
     * @return Das UserModel-Objekt des Benutzers
     */
    UserModel findByEmail(String email);
}