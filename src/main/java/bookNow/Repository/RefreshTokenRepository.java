package bookNow.Repository;

import bookNow.Model.CompanyModel;
import bookNow.Model.RefreshTokenModel;
import bookNow.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Dieses Repository-Interface ermöglicht den Zugriff auf Refresh-Token-Daten und bietet CRUD-Operationen.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, Long> {

    /**
     * Findet ein Refresh-Token basierend auf dem Benutzer.
     * @param user der Benutzer, für den das Refresh-Token gesucht wird
     * @return Das RefreshTokenModel-Objekt
     */
    RefreshTokenModel findByUser(UserModel user);

    /**
     * Findet ein Refresh-Token basierend auf dem Unternehmen.
     * @param company das Unternehmen, für das das Refresh-Token gesucht wird
     * @return Das RefreshTokenModel-Objekt
     */
    RefreshTokenModel findByCompany(CompanyModel company);

    /**
     * Findet ein Refresh-Token basierend auf der Benutzer-ID.
     * @param userId die ID des Benutzers
     * @return Das RefreshTokenModel-Objekt
     */
    RefreshTokenModel findByUser_Id(Long userId);

    /**
     * Findet ein Refresh-Token basierend auf der Unternehmens-ID.
     * @param companyId die ID des Unternehmens
     * @return Das RefreshTokenModel-Objekt
     */
    RefreshTokenModel findByCompany_Id(Long companyId);
}
